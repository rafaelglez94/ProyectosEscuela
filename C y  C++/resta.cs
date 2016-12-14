using System.IO;
using System;

class Program
{
    static void Main(string[] args)
    {
    	Console.WriteLine("Ingrese los numeros que quiere restar");
        Console.WriteLine("Numero 1:");
        int numero_1 = Convert.ToInt32(Console.ReadLine());
        Console.WriteLine("Numero 2:");
        int numero_2 = Convert.ToInt32(Console.ReadLine());
        int total= numero_1-numero_2;
        Console.WriteLine("El total de la resta es: "+total);
    }
}
