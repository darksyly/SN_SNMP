import net.percederberg.mibble.*;
import net.percederberg.mibble.value.ObjectIdentifierValue;
import org.snmp4j.CommunityTarget;
import org.snmp4j.PDU;
import org.snmp4j.Snmp;
import org.snmp4j.TransportMapping;
import org.snmp4j.event.ResponseEvent;
import org.snmp4j.mp.SnmpConstants;
import org.snmp4j.smi.*;
import org.snmp4j.transport.DefaultUdpTransportMapping;
import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.HashMap;
import java.util.Scanner;

public class SNMP_Tool {
    public static String READ_COMMUNITY = "public";
    public static String OID;
    public static HashMap<String, ObjectIdentifierValue> Mibmap = new HashMap<>();
    public static MibLoader loader = new MibLoader();

    public static void main(String[] args) {
        try {

            //anfänglich werden zwei Mib files eingelesen, der benutzer kann selber noch mehr laden
            Mib mib1 = loader.load("RFC1213-MIB");
            Mib mib2 = loader.load("HOST-RESOURCES-MIB");

            //Werte in Hashmap speichern
            extractOids(mib1);
            extractOids(mib2);

            SNMP_Tool objSNMP = new SNMP_Tool();
            Scanner sc = new Scanner(System.in);

            String UserIn;
            String strIPAddress = "127.0.0.1";

            System.out.println("//SNMP TOOL//");
            while(true) {
                System.out.println("\n--Enter Command--");
                UserIn = sc.nextLine();
                switch(UserIn) {
                    case "/help":
                        System.out.println("Commands: /scan /setIp /getInfo /getOid /loadMib /changeCom");
                        break;
                    case "/setIp":
                        System.out.println("Enter Destination IP Adress (Example: 127.0.0.1):");
                        strIPAddress = sc.nextLine();
                        break;
                    case "/scan":
                        System.out.println("Enter Destination Network (Example: 10.10.30.0):");
                        UserIn = sc.nextLine();
                        objSNMP.getNetwork(UserIn);
                        break;
                    case "/changeCom":
                        System.out.println("Enter new CommunityString: (Example: public)");
                        UserIn = sc.nextLine();
                        changeCom(UserIn);
                        break;
                    case "/loadMib":
                        System.out.println("Enter Path/Name of Mib File:");
                        UserIn = sc.nextLine();
                        mibLoader(UserIn);
                        break;
                    case "/getOid":
                        System.out.println("\nEnter a OID: (Example: 1.3.6.1.2.1.1.1.0 or \"sysName\" if needed MibFile loaded)");
                        OID = sc.nextLine();
                        OID = mibChecker(OID);
                        if((! objSNMP.snmpGet(strIPAddress, READ_COMMUNITY, OID).equals(""))) {
                            System.out.println(objSNMP.snmpGet(strIPAddress, READ_COMMUNITY, OID));
                        } else {
                            System.out.println("No valid OID or IP with SNMP not activated (Try to change IP or try other OID)");
                        }
                        break;
                    case "/getInfo":
                        if(objSNMP.snmpGet(strIPAddress, READ_COMMUNITY, "1.3.6.1.2.1.1.1.0") != null) {
                            System.out.println("\nGeneral informations about IP: " + strIPAddress);
                            System.out.println("sysDescription:" + objSNMP.snmpGet(strIPAddress, READ_COMMUNITY, "1.3.6.1.2.1.1.1.0"));
                            System.out.println("sysObjectID:" + objSNMP.snmpGet(strIPAddress, READ_COMMUNITY, "1.3.6.1.2.1.1.2.0"));
                            System.out.println("sysUpTime:" + objSNMP.snmpGet(strIPAddress, READ_COMMUNITY, "1.3.6.1.2.1.1.3.0"));
                            System.out.println("sysContact:" + objSNMP.snmpGet(strIPAddress, READ_COMMUNITY, "1.3.6.1.2.1.1.4.0"));
                            System.out.println("sysName:" + objSNMP.snmpGet(strIPAddress, READ_COMMUNITY, "1.3.6.1.2.1.1.5.0"));
                            System.out.println("sysLocation:" + objSNMP.snmpGet(strIPAddress, READ_COMMUNITY, "1.3.6.1.2.1.1.6.0"));
                            System.out.println("sysServices:" + objSNMP.snmpGet(strIPAddress, READ_COMMUNITY, "1.3.6.1.2.1.1.7.0"));
                        } else {
                            System.out.println("No Information available (Try to change IP)");
                        }
                        break;
                    default:
                        System.out.println("Command: " + UserIn + " not found - Type /help to see all commands");
                }
            }
        } catch(Exception e) {
            e.printStackTrace();
        }
    }

    public String snmpGet(String strAddress, String community, String strOID) {

        String str = "";
        try {
            OctetString community1 = new OctetString(community);
            strAddress = strAddress + "/" + 161;
            Address targetaddress = new UdpAddress(strAddress);
            TransportMapping transport = new DefaultUdpTransportMapping();
            transport.listen();
            CommunityTarget comtarget = new CommunityTarget();
            comtarget.setCommunity(community1);
            comtarget.setVersion(SnmpConstants.version1);
            comtarget.setAddress(targetaddress);
            comtarget.setRetries(2);
            comtarget.setTimeout(5000);
            PDU pdu = new PDU();
            ResponseEvent response;
            Snmp snmp;
            pdu.add(new VariableBinding(new OID(strOID)));
            pdu.setType(PDU.GET);
            snmp = new Snmp(transport);
            response = snmp.get(pdu, comtarget);
            if(response != null) {
                try {
                    if(response.getResponse().getErrorStatusText().equalsIgnoreCase("Success")) {
                        PDU pduresponse = response.getResponse();
                        str = pduresponse.getVariableBindings().firstElement().toString();
                        if(str.contains("=")) {
                            int len = str.indexOf("=");
                            str = str.substring(len + 1, str.length());
                        }
                    }
                } catch(NullPointerException e) {
                    str = null;
                }
            } else {
                System.out.println("Error: Timeout");
            }
            snmp.close();
        } catch(Exception e) {
            e.printStackTrace();
        }
        return str;
    }

    public void getNetwork(String network) {

        InetAddress localhost;
        byte[] ip = null;
        try {
            localhost = InetAddress.getByName(network);
            ip = localhost.getAddress();
        } catch(UnknownHostException e) {
            e.printStackTrace();
        }

        System.out.println("Scanning all adresses of your Network, this may take several minutes...");
        for(int i = 1; i <= 254; i++) {
            try {
                ip[3] = (byte) i;
                InetAddress address = InetAddress.getByAddress(ip);
                if(address.isReachable(30)) {
                    if(snmpGet(String.valueOf(address), "public", "1.3.6.1.2.1.1.4.0") != null) {
                        String output = address.toString().substring(1);
                        System.out.print("\n" + output + " is on the network and SNMP is ACTIVE");
                    } else {
                        String output = address.toString().substring(1);
                        System.out.print("\n" + output + " is on the network and SNMP is NOT active");
                    }
                }
            } catch(IOException e) {
                e.printStackTrace();
            }
        }
        System.out.println("\nScan finished!");

    }

    public static void extractOids(Mib mib) {
        for(Object symbol : mib.getAllSymbols()) {
            ObjectIdentifierValue oid = extractOid((MibSymbol) symbol);
            if(oid != null) {
                Mibmap.put(((MibSymbol) symbol).getName(), oid);
            }
        }
    }

    //OID wird extrahiert
    public static ObjectIdentifierValue extractOid(MibSymbol symbol) {
        if(symbol instanceof MibValueSymbol) {
            MibValue value = ((MibValueSymbol) symbol).getValue();
            if(value instanceof ObjectIdentifierValue) {
                return (ObjectIdentifierValue) value;
            }
        }
        return null;
    }

    public static String mibChecker(String s) {
        if(Mibmap.containsKey(s)) {
            return Mibmap.get(s).toString() + ".0";
        } else {
            return s;
        }
    }

    public static void mibLoader(String s) {
        try {
            Mib mib = loader.load(s);
            extractOids(mib);
        } catch(IOException | MibLoaderException e) {
            e.printStackTrace();
            System.out.println("Load Failed");
        }
    }

    public static void changeCom(String s) {
        READ_COMMUNITY = s;
    }

}