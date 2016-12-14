using System.IO;
using System;

class Program
{
    static void Main(string[] args)
    {
		Console.WriteLine("Dame un numero:");
        int numero_1 = Convert.ToInt32(Console.ReadLine());
        if(numero_1%2==0){
	        Console.WriteLine("El numero ingresado es par ");
        }else{
 	       	Console.WriteLine("El numero ingresado es impar");
        }
    }
}
