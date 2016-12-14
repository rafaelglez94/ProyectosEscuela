				TITLE	Programa para evaluar una expresión
				.586
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
PromptA			BYTE	"Proporcione el valor de A: "
Long_PA			EQU		$ - PromptA
PromptB			BYTE	"Proporcione el valor de B: "
Long_PB			EQU		$ - PromptB
PromptC			BYTE	"Proporcione el valor de C: "
Long_PC			EQU		$ - PromptC
Men_Sal			BYTE	"El valor de E es: "
Long_MS			EQU		$ - Men_Sal
Texto			BYTE	11 DUP ( ? )
Caracteres		DWORD	?
ManejadorE		DWORD	?
ManejadorS		DWORD	?
SaltoDeLinea	BYTE	13, 10

E				DWORD	0
A				DWORD	0
B				DWORD	0
C1				DWORD	0
				.CODE
Main			PROC
				; Obtener manejadores de entrada y salida
				INVOKE	GetStdHandle, STD_INPUT_HANDLE
				MOV		ManejadorE, EAX
				INVOKE	GetStdHandle, STD_OUTPUT_HANDLE
				MOV		ManejadorS, EAX

				; Obtener el valor de A
				MOV		EAX, Long_PA
				INVOKE	WriteConsoleA, ManejadorS, ADDR PromptA, EAX, ADDR Caracteres, 0
				INVOKE	ReadConsoleA, ManejadorE, ADDR Texto, 11, ADDR Caracteres, 0
				SUB		Caracteres, 2
				MacroCadenaAEntero	Texto, A

				; Obtener el valor de B
				MOV		EAX, Long_PB
				INVOKE	WriteConsoleA, ManejadorS, ADDR PromptB, EAX, ADDR Caracteres, 0
				INVOKE	ReadConsoleA, ManejadorE, ADDR Texto, 11, ADDR Caracteres, 0
				SUB		Caracteres, 2
				MacroCadenaAEntero	Texto, B

				; Obtener el valor de C
				MOV		EAX, Long_PC
				INVOKE	WriteConsoleA, ManejadorS, ADDR PromptC, EAX, ADDR Caracteres, 0
				INVOKE	ReadConsoleA, ManejadorE, ADDR Texto, 11, ADDR Caracteres, 0
				SUB		Caracteres, 2
				MacroCadenaAEntero	Texto, C1
				
				; Evaluar la expresión
				; 4 * A
				MOV		EAX, 4
				IMUL		A
				; (4 * A * B)
				IMUL		B
				MOV		EBX, EAX
				; (C * C)
				MOV		EAX, C1
				IMUL		C1
				;(4 * A * B) - (C * C)
				SUB		EBX, EAX
				MOV		E, EBX
				
				; Imprimir el resultado
				MOV		EAX, Long_MS
				INVOKE	WriteConsoleA, ManejadorS, ADDR Men_Sal, EAX, ADDR Caracteres, 0
				;Convertir texto a cadena
				MacroEnteroACadena E, Texto, Caracteres
				;Imprimir Numero
				INVOKE	WriteConsoleA, ManejadorS, ADDR Texto, Caracteres, ADDR Caracteres, 0
				
				; Salto de Linea
				INVOKE	WriteConsoleA, ManejadorS, ADDR SaltoDeLinea, 2, ADDR Caracteres, 0
				
				; Salir
				INVOKE	ExitProcess, 0								
Main			ENDP
				END Main