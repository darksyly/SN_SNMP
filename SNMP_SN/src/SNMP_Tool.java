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
import java.util.Scanner;

public class SNMP_Tool {
    public static final String READ_COMMUNITY = "public";
    public static String OID;

    public static void main(String[] args){
        try{

            SNMP_Tool objSNMP = new SNMP_Tool();
            Scanner sc = new Scanner(System.in);

            String UserIn;
            String strIPAddress = "127.0.0.1";

            System.out.println("//SNMP TOOL//");
            while(true){
                System.out.println("\n--Enter Command--");
                UserIn = sc.nextLine();
                switch(UserIn){
                    case "/help":
                        System.out.println("Commands: /scan /getOID /set ip /get Infos");
                        break;
                    case "/set ip":
                        System.out.println("Enter Destination IP Adress (Example: 127.0.0.1):");
                        strIPAddress = sc.nextLine();
                        break;
                    case "/scan":
                        System.out.println("Enter Destination Network (Example: 10.10.30.0):");
                        UserIn = sc.nextLine();
                        objSNMP.getNetwork(UserIn);
                        break;
                    case "/getOID":
                        System.out.println("\nEnter a OID: (Example: 1.3.6.1.2.1.1.1.0)");
                        OID = sc.nextLine();
                        if((objSNMP.snmpGet(strIPAddress, READ_COMMUNITY, OID) != "")){
                            System.out.println(objSNMP.snmpGet(strIPAddress, READ_COMMUNITY, OID));
                        }else{
                            System.out.println("No valid OID or IP with no SNMP activated");
                        }
                        break;
                    case "/get Infos":
                        if(objSNMP.snmpGet(strIPAddress,READ_COMMUNITY,"1.3.6.1.2.1.1.1.0") != null){
                            System.out.println("\nGeneral informations about IP: " + strIPAddress);
                            System.out.println("sysDescription:" + objSNMP.snmpGet(strIPAddress,READ_COMMUNITY,"1.3.6.1.2.1.1.1.0"));
                            System.out.println("sysObjectID:" +  objSNMP.snmpGet(strIPAddress,READ_COMMUNITY,"1.3.6.1.2.1.1.2.0"));
                            System.out.println("sysUpTime:" + objSNMP.snmpGet(strIPAddress,READ_COMMUNITY,"1.3.6.1.2.1.1.3.0"));
                            System.out.println("sysContact:" + objSNMP.snmpGet(strIPAddress,READ_COMMUNITY,"1.3.6.1.2.1.1.4.0"));
                            System.out.println("sysName:" + objSNMP.snmpGet(strIPAddress,READ_COMMUNITY,"1.3.6.1.2.1.1.5.0"));
                            System.out.println("sysLocation:" + objSNMP.snmpGet(strIPAddress,READ_COMMUNITY,"1.3.6.1.2.1.1.6.0"));
                            System.out.println("sysServices:" + objSNMP.snmpGet(strIPAddress,READ_COMMUNITY,"1.3.6.1.2.1.1.7.0"));
                        }else{
                            System.out.println("No Information available (Try to change IP)");
                        }
                        break;
                    default: System.out.println("Command: " + UserIn + " not found - Type /help to see all commands");
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public String snmpGet(String strAddress, String community, String strOID){

        String str="";
        try{
            OctetString community1 = new OctetString(community);
            strAddress= strAddress+"/" + 161;
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
            response = snmp.get(pdu,comtarget);
            if(response != null){
                try {
                    if(response.getResponse().getErrorStatusText().equalsIgnoreCase("Success")) {
                        PDU pduresponse = response.getResponse();
                        str = pduresponse.getVariableBindings().firstElement().toString();
                        if(str.contains("=")) {
                            int len = str.indexOf("=");
                            str = str.substring(len + 1, str.length());
                        }
                    }
                }catch(NullPointerException e){
                    str = null;
                }
            }else{
                System.out.println("Error: Timeout");
            }
            snmp.close();
        } catch(Exception e) { e.printStackTrace(); }
        return str;
    }

    public void getNetwork(String network){

        InetAddress localhost;
        byte[] ip = null;
        try {
            localhost = InetAddress.getByName(network);
            ip = localhost.getAddress();
        } catch(UnknownHostException e) {
            e.printStackTrace();
        }



        System.out.println("Scanning all adresses of your Network, this may take several minutes...");
        for (int i = 1; i <= 254; i++) {
            try {
                ip[3] = (byte) i;
                InetAddress address = InetAddress.getByAddress(ip);
                if (address.isReachable(30)) {
                    if(snmpGet(String.valueOf(address), "public", "1.3.6.1.2.1.1.4.0") != null){
                        String output = address.toString().substring(1);
                        System.out.print("\n" + output + " is on the network and SNMP is ACTIVE");
                    }else{
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
}