using System.IO;
using System;

class Program
{
    static void Main(string[] args)
    {
		Console.WriteLine("Tabla que quiere:");
        int numero = Convert.ToInt32(Console.ReadLine());
        for(int i= 0; i<=10;i++){
			Console.WriteLine( numero +" X "+ i +" X "+ (numero * i) );
        }
    }
}
