import org.snmp4j.CommunityTarget;
import org.snmp4j.PDU;
import org.snmp4j.Snmp;
import org.snmp4j.TransportMapping;
import org.snmp4j.event.ResponseEvent;
import org.snmp4j.event.ResponseListener;
import org.snmp4j.mp.SnmpConstants;
import org.snmp4j.smi.*;
import org.snmp4j.transport.DefaultUdpTransportMapping;

import java.util.Scanner;

public class test {
    public static final String READ_COMMUNITY = "public";
    public static String OID;

    public static void main(String[] args){
        try{

            String strIPAddress;
            test objSNMP = new test();

            //----------------------------
            Scanner sc = new Scanner(System.in);
            System.out.println("Enter Destination IP Adress (Example: 127.0.0.1):");
            strIPAddress = sc.nextLine();
            //----------------------------

            //----------------------------
            System.out.println("\nGeneral informations about IP: " + strIPAddress + "\n\nsysDescr:");
            objSNMP.snmpGet(strIPAddress,READ_COMMUNITY,"1.3.6.1.2.1.1.1.0");
            System.out.println("sysObjectID:");
            objSNMP.snmpGet(strIPAddress,READ_COMMUNITY,"1.3.6.1.2.1.1.2.0");
            System.out.println("sysUpTime:");
            objSNMP.snmpGet(strIPAddress,READ_COMMUNITY,"1.3.6.1.2.1.1.3.0");
            System.out.println("sysContact:");
            objSNMP.snmpGet(strIPAddress,READ_COMMUNITY,"1.3.6.1.2.1.1.4.0");
            System.out.println("sysName:");
            objSNMP.snmpGet(strIPAddress,READ_COMMUNITY,"1.3.6.1.2.1.1.5.0");
            System.out.println("sysLocation:");
            objSNMP.snmpGet(strIPAddress,READ_COMMUNITY,"1.3.6.1.2.1.1.6.0");
            System.out.println("sysServices:");
            objSNMP.snmpGet(strIPAddress,READ_COMMUNITY,"1.3.6.1.2.1.1.7.0");
            //----------------------------

            //----------------------------
            System.out.println("\nEnter a OID: (Example: 1.3.6.1.2.1.1.1.0)");
            OID = sc.nextLine();
            objSNMP.snmpGet(strIPAddress,READ_COMMUNITY,OID);
            //----------------------------
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
                if(response.getResponse().getErrorStatusText().equalsIgnoreCase("Success")){
                    PDU pduresponse=response.getResponse();
                    str=pduresponse.getVariableBindings().firstElement().toString();
                    if(str.contains("=")){
                        int len = str.indexOf("=");
                        str=str.substring(len+1, str.length());
                    }
                }
            }else{
                System.out.println("Error: Timeout");
            }
            snmp.close();
        } catch(Exception e) { e.printStackTrace(); }
        System.out.println(str);
        return str;
    }

}