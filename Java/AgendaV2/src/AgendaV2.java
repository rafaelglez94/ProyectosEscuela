/*
INSTITUTO TECNOLÓGICO DE CULIACÁN
INGENIERÍA EN SISTEMAS COMPUTACIONALES

TOPICOS AVANZADOS DE PROGRAMACIÓN
CLEMENTE GARCIA GERARDO

PROYECTO DE MÓVILES
AGENDA TELEFÓNICA

GONZALEZ CASTRO RAFAEL

GRUPO DE 12:00PM - 01:00PM
*/
import java.io.*;
import javax.microedition.lcdui.*;
import javax.microedition.midlet.*;
import javax.microedition.rms.RecordStore;

public class AgendaV2 extends MIDlet implements CommandListener{
    Display P;
    Form F[];
    List L;
    Command Btn[];
    TextField []TxtLogin,TxtAgenda;
    Contacto C[];
    int IndC=-1;
    RecordStore Rs;
    ByteArrayInputStream Bais;
    DataInputStream Dis;
    ByteArrayOutputStream Baos;
    DataOutputStream Dos;
    byte[] Registro;
    public AgendaV2(){
        P = Display.getDisplay(this);  
        HazInterfaz();
        HazEscuchas();
        ObtenerContactos();
    }
    public void HazInterfaz(){
        F= new Form[2];
        String txt1[]={"Login","Agenda"};
        for(int i=0;i<F.length;i++)
            F[i]=new Form(txt1[i]);
        Btn= new Command[8];
        String txt2[]={"Salir","Entrar","Guardar","Consultar","Actualizar","Borrar","Atras","Limpiar"};
        for(int i=0;i<Btn.length;i++)
            Btn[i] = new Command(txt2[i], Command.SCREEN, 0);
        HazLogin();
        HazAgenda();
        HazConsulta();
    }
    public void HazLogin(){
        TxtLogin= new TextField[2];
        String txt[]={"Usuario","NIP"};
        String txt1[]={"rafagc","123"};
        for(int i=0;i<2;i++){
            F[0].addCommand(Btn[i]);
            F[0].append(TxtLogin[i]= new TextField(txt[i],txt1[i],15,(i==0?TextField.ANY:TextField.PASSWORD)));
        } 
        
    }
    public void HazAgenda(){
        TxtAgenda= new TextField[4];
        String txt[]={"Teléfono","Nombre","Apellido","Apodo"};
        for(int i=0;i<8;i++)
            if(i!=1 && i!=6)
                F[1].addCommand(Btn[i]);
        for(int i=0;i<4;i++)
            F[1].append(TxtAgenda[i]= new TextField(txt[i],"",(i==0?10:15),(i==0?TextField.PHONENUMBER:TextField.ANY)));
    }
    public void HazConsulta(){
        L= new List("Consulta",List.IMPLICIT);
        L.addCommand(Btn[4]);
        L.addCommand(Btn[6]);
    }
    public void HazEscuchas(){
        F[0].setCommandListener(this);
        F[1].setCommandListener(this);
        L.setCommandListener(this);
    }
    public void commandAction(Command Com, Displayable arg1){
        if(Com==Btn[0]){
            destroyApp(true);
            notifyDestroyed();
            return;
        }
        if(arg1==F[0]){
            if(Com==Btn[1]){
                for(int i=0;i<2;i++)
                    if(TxtLogin[i].getString().length()==0){
                         P.setCurrent(new Alert("Campo Vacio","Llene las Capos Necesarios",null,AlertType.ERROR));
                        return;
                    }
                if(!TxtLogin[0].getString().equals("rafagc")){
                    P.setCurrent(new Alert("Usuario Incorrecto","El Usuario que ingreso es incorrecto",null,AlertType.ERROR));
                    return;
                }
                if(!TxtLogin[1].getString().equals("123")){
                    P.setCurrent(new Alert("NIP Incorrecto","El NIP que ingreso es incorrecto",null,AlertType.ERROR));
                    return;
                }
                F[1].setTitle("Agenda de: Rafael Gonzalez");
                P.setCurrent(F[1]);
                return;
            }   
        }
        if(arg1==F[1]){
            if(Com==Btn[2]){
                if(IndC==C.length){
                    P.setCurrent(new Alert("Agenda Llena","No se puede insertar mas contactos",null,AlertType.ERROR));
                    return;
                }
                for(int i=0;i<4;i++)
                    if(TxtAgenda[i].getString().length()==0){
                         P.setCurrent(new Alert("Campo "+TxtAgenda[i].getLabel()+" Vacio","Llene las Capos Necesarios",null,AlertType.ERROR));
                        return;
                    }
                GuardarUsuario();
                Limpiar();
                return;
            }
            if(Com==Btn[3]){
                if(IndC==-1){
                     P.setCurrent(new Alert("Agenda sin contactos","Inserte contactos para consultar",null,AlertType.ERROR));
                     return;
                }
               String Orden="";
                for(int i=0;i<4;i++)
                    if(TxtAgenda[i].getString().length()!=0){
                          Orden+=i+"";
                    }
                Consultar(Orden);
                P.setCurrent(L);
                return;
            }
            if(Com==Btn[4]){
                for(int i=0;i<4;i++)
                    if(TxtAgenda[i].getString().length()==0){
                         P.setCurrent(new Alert("Campo "+TxtAgenda[i].getLabel()+" Vacio","Llene las Capos Necesarios",null,AlertType.ERROR));
                        return;
                    }
                ActualizarUsuario();
                Limpiar();
                return;
            }
            if(Com==Btn[5]){             
                if(TxtAgenda[0].getString().length()==0){
                       P.setCurrent(new Alert("Campo "+TxtAgenda[0].getLabel()+" Vacio","Llene las Capos Necesarios",null,AlertType.ERROR));
                      return;
                }
                EliminarUsuario();
                Limpiar();
                return;
            }
            if(Com==Btn[7]){             
                Limpiar();
                return;
            }
        }
       if(arg1==L){
           if(Com==Btn[6]){
                P.setCurrent(F[1]);
                return;
           }
           String Cad=L.getString(L.getSelectedIndex());
           String numero="";
           for(int i=0;i<Cad.length();i++){
                if(!Character.isDigit(Cad.charAt(i)))
                    break;
                numero+=Cad.charAt(i);
           }
           int IE=Integer.parseInt(numero);
            TxtAgenda[0].setString(""+C[IE].Telefono);
            TxtAgenda[1].setString(Contacto.SinRell(C[IE].Nombre));
            TxtAgenda[2].setString(Contacto.SinRell(C[IE].Apellido));
            TxtAgenda[3].setString(Contacto.SinRell(C[IE].Apodo));
            P.setCurrent(F[1]);
           return;
       }
    }
    public void Limpiar(){
        for(int i=0;i<TxtAgenda.length;i++)
            TxtAgenda[i].setString("");
    }
    public void Consultar(String Orden){
        L.deleteAll();
        if(Orden.length()==0){
            for(int i=0;i<=IndC;i++){
                L.append(i+" "+C[i].toString(), null);
            }
            return;
        }
        Contacto aux= new Contacto();
        aux.Telefono=Long.parseLong(TxtAgenda[0].getString().length()==0?"0":TxtAgenda[0].getString());
        aux.Nombre=TxtAgenda[1].getString();
        aux.Apellido=TxtAgenda[2].getString();
        aux.Apodo=TxtAgenda[3].getString();
        int pos=BuscarContacto(aux,Orden);
        if(pos==-1){
            P.setCurrent(new Alert("Sin coincidencias","No Hay Datos",null,AlertType.ERROR));
        }
        int auxP=pos;
        while(auxP<=IndC && C[auxP].Cadena.compareTo(C[pos].Cadena)==0){
            L.append(auxP+" "+C[auxP].toString(), null);
            auxP++;
        }
    }
    public void GuardarUsuario(){
        Contacto aux= new Contacto();
        aux.Telefono=Long.parseLong(TxtAgenda[0].getString());
        aux.Nombre=TxtAgenda[1].getString();
        aux.Apellido=TxtAgenda[2].getString();
        aux.Apodo=TxtAgenda[3].getString();
        int pos=BuscarContacto(aux,"0");
        if(pos!=-1){
            P.setCurrent(new Alert("Contacto Duplicado","Telefono no disponible",null,AlertType.ERROR));
            return;
        }
        IndC++;
        C[IndC]= aux;
        P.setCurrent(new Alert("Contacto Registrado","Telefono: "+C[IndC].Telefono+"\nNombre: "+C[IndC].Nombre,null,AlertType.ERROR));        
    }
    public void ActualizarUsuario(){
        Contacto aux= new Contacto();
        aux.Telefono=Long.parseLong(TxtAgenda[0].getString());
        aux.Nombre=TxtAgenda[1].getString();
        aux.Apellido=TxtAgenda[2].getString();
        aux.Apodo=TxtAgenda[3].getString();
        int pos=BuscarContacto(aux,"0");
        if(pos==-1){
            P.setCurrent(new Alert("Contacto No Entontrado","El teléfono ingresado no esta registrado",null,AlertType.ERROR));
            return;
        }
        C[pos]= aux;
        P.setCurrent(new Alert("Contacto Actualizado","Telefono: "+C[pos].Telefono+"\nNombre: "+C[pos].Nombre+"\nApellido: "+C[pos].Apellido+"\nApodo: "+C[pos].Apodo,null,AlertType.ERROR));        
    }
    public void EliminarUsuario(){
        Contacto aux= new Contacto();
        aux.Telefono=Long.parseLong(TxtAgenda[0].getString());
        int pos=BuscarContacto(aux,"0");
        if(pos==-1){
            P.setCurrent(new Alert("Contacto No Entontrado","El teléfono ingresado no esta registrado",null,AlertType.ERROR));
            return;
        }
        for(int i=pos;i<IndC;i++)
            C[i]=C[i+1];
        C[IndC]= new Contacto();
        IndC--;
        P.setCurrent(new Alert("Contacto Eliminado","El Contacto con Teléfono: "+TxtAgenda[0].getString()+"\n Ya no existe",null,AlertType.ERROR));        
    }
    public void startApp() {
        P.setCurrent(F[0]);
    }
    public void pauseApp() {}
    public void destroyApp(boolean unconditional){
        EstablecerContactos();
    }
    public int BuscarContacto(Contacto Dato,String Orden) {
        OrdenarVectorContacto(Orden);
        Dato.Ordenar(Orden);
        int ind=0,inicio=0,fin=IndC,pos;
        while (inicio <= fin) {
            pos = (inicio+fin) / 2;
            if (C[pos].Cadena.compareTo(Dato.Cadena)==0){
                while(pos>-1 && C[pos].Cadena.compareTo(Dato.Cadena)==0)
                    pos--;
                pos++;
                return pos;
            }
            else if(C[pos].Cadena.compareTo(Dato.Cadena)<0)
                inicio = pos+1;
            else 
                fin = pos-1;
        }
        return -1;
    }
    public void OrdenarVectorContacto(String Orden){
        for(int i=0;i<=IndC;i++)
            C[i].Ordenar(Orden);
        for(int i=0;i<=IndC-1;i++)
            for(int j=1+i;j<=IndC;j++)
                if(C[i].Cadena.compareTo(C[j].Cadena)>0){
                    Contacto aux=C[j];
                    C[j]=C[i];
                    C[i]=aux;
                }
    }  
    public void ObtenerContactos(){
        Registro = new byte[500];
        try {
            Rs = RecordStore.openRecordStore("RSContactos",false);
            Bais = new ByteArrayInputStream(Registro);
            Dis = new DataInputStream(Bais);    
            int totC=Rs.getNumRecords();
            C=new Contacto[totC+10];
            System.out.println(totC);
            IndC=-1;
            for (int i=1;i<=totC;i++){
                Rs.getRecord(i,Registro,0);
                IndC++;
                C[IndC]= new Contacto();
                C[IndC].Telefono=Dis.readLong();
                C[IndC].Nombre=Dis.readUTF();
                C[IndC].Apellido=Dis.readUTF();
                C[IndC].Apodo=Dis.readUTF();
                Bais.reset();
                System.out.println(C[IndC].toString());
            }
            Bais.close();
            Dis.close();
            Rs.closeRecordStore();
        }catch (Exception e){
            C=new Contacto[20];
        }
        Registro = null;
    }
    public void EstablecerContactos(){
         Registro = new byte[300];
        try{
            RecordStore.deleteRecordStore("RSContactos");
        }catch (Exception e){}
        try{
            Rs = RecordStore.openRecordStore("RSContactos",true);
            Baos = new ByteArrayOutputStream();
            Dos = new DataOutputStream(Baos);
            for(int i=0;i<=IndC;i++){
                Dos.writeLong(Long.parseLong(Contacto.RellNum(C[i].Telefono+"")));
                Dos.writeUTF(Contacto.RellCad(C[i].Nombre));
                Dos.writeUTF(Contacto.RellCad(C[i].Apellido));
                Dos.writeUTF(Contacto.RellCad(C[i].Apodo));
                Dos.flush();
                Registro = Baos.toByteArray();
                Rs.addRecord(Registro,0,Registro.length);
                Baos.reset();
                System.out.println("Dato Guardado");
            }
            Baos.close();
            Dos.close();
            Rs.closeRecordStore();
        }  catch (Exception e){   
            System.out.println("Error: "+e);  
        }
    }
}
class Contacto{
    long Telefono;
    String Nombre;
    String Apellido;
    String Apodo;
    String Cadena;
    public String toString(){
        return RellCad(Telefono+"")+RellCad(Nombre)+RellCad(Apellido)+RellCad(Apodo);
    }
    public static String RellCad(String Dato){
        for(int i=Dato.length()-1;i<20;i++)
            Dato+=" ";
        return Dato;
    }
    public static String RellNum(String Dato){
        for(int i=Dato.length()-1;i<15;i++)
            Dato="0"+Dato;
        return Dato;
    }
    public static String SinRell(String Dato){
        String Cad="";
        for(int i=0;i<Dato.length();i++){
            if(Dato.charAt(i)==32)
                break;
            Cad+=Dato.charAt(i); 
        }
        return Cad;
    }
    public void Ordenar(String Orden){
        Cadena="";
        for(int i=0;i<Orden.length();i++){
            switch(Orden.charAt(i)){
                case '0':
                    Cadena+=RellNum(Telefono+"");
                    break;
                case '1':
                    Cadena+=RellCad(Nombre);
                    break;
                case '2':
                    Cadena+=RellCad(Apellido);
                    break;
                case '3':
                    Cadena+=RellCad(Apodo);
                    break;
            }
        }
    }
}