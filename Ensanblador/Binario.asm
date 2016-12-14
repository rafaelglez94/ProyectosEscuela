				TITLE	Programa para convertir de binario a decimal
				.686
				.MODEL	FLAT, STDCALL

;Prototipos de funciones a utilizar
GetStdHandle	PROTO	:DWORD
ReadConsoleA	PROTO	:DWORD, :DWORD, :DWORD, :DWORD, :DWORD
WriteConsoleA	PROTO	:DWORD, :DWORD, :DWORD, :DWORD, :DWORD
ExitProcess		PROTO	:DWORD

INCLUDE <Macros.inc>

		.STACK
		.DATA
STD_INPUT_HANDLE	EQU	-10
STD_OUTPUT_HANDLE	EQU	-11
PromptA			BYTE	"Proporcione un numero binario de 8 bits: "
Long_PA			EQU		$ - PromptA
Men_Sal			BYTE	"El valor en decimal es: "
Long_MS			EQU		$ - Men_Sal
SaltoLinea		BYTE	13,10
Texto			BYTE	10 DUP ( ? )
Caracteres		DWORD	?
ManejadorE		DWORD	?
ManejadorS		DWORD	?

Numero			DWORD	0

				.CODE
Main			PROC
				; Obtener manejadores de entrada y salida
				INVOKE	GetStdHandle, STD_INPUT_HANDLE
				MOV		ManejadorE, EAX
				INVOKE	GetStdHandle, STD_OUTPUT_HANDLE
				MOV		ManejadorS, EAX

				; Obtener el numero binario
				MOV		EAX, Long_PA
				INVOKE	WriteConsoleA, ManejadorS, ADDR PromptA, EAX, ADDR Caracteres, 0
				INVOKE	ReadConsoleA, ManejadorE, ADDR Texto, 10, ADDR Caracteres, 0
				
				; Convertir Binario a decimal
				MOV		EBX, 0				; Posicion del primer caracter de Texto
Inicio:				
				CMP		Texto[EBX], '0'
				JB		Terminar
				CMP		Texto[EBX], '1'
				JA		Terminar
				
				; Multiplicar por 2
				MOV		EAX, 2
				IMUL	Numero
				MOV		Numero, EAX
				
				; Convertir caracter en digito
				MOV		AL, Texto[EBX]
				AND		EAX, 0000000FH
				
				; Sumar digito a numero
				ADD		Numero, EAX
				
				; Incrementar indice
				INC		EBX
				
				; Regresar al inicio
				JMP		Inicio
				
Terminar:
				; Imprimir el resultado
				MOV		EAX, Long_MS
				INVOKE	WriteConsoleA, ManejadorS, ADDR Men_Sal, EAX, ADDR Caracteres, 0
				;Convertir entero a cadena
				MacroEnteroACadena Numero, Texto, Caracteres
				;Imprimir Numero
				INVOKE	WriteConsoleA, ManejadorS, ADDR Texto, Caracteres, ADDR Caracteres, 0
				; Salto de linea
				INVOKE	WriteConsoleA, ManejadorS, ADDR SaltoLinea, 2, ADDR Caracteres, 0
				; Salir
				INVOKE	ExitProcess, 0								
Main			ENDP
				END Main
				