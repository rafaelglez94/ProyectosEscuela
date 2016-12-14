using System.IO;
using System;

class Program
{
    static void Main()
    {
        int numero;
        do{
        Console.WriteLine("0.- Salir");
        Console.WriteLine("Numero de Tabla:");
        numero = Convert.ToInt32(Console.ReadLine());
        if(numero!=0){
            for(int i= 0; i<=10;i++){
    			Console.WriteLine( numero +" X "+ i +" X "+ (numero * i) );
            }
        }
        }while(numero!=0);
    }
}
