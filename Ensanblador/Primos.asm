				TITLE	Programa para evaluar un nùmero primo
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
Prompt			BYTE	"Proporcione un numero entero positivo: "
Long_P			EQU		$ - Prompt
Men_Primo		BYTE	"El numero es primo.", 13, 10
Long_MP			EQU		$ - Men_Primo
Men_NoPrimo		BYTE	"El numero es no primo.", 13, 10
Long_MNP		EQU		$ - Men_NoPrimo
Texto			BYTE	13 DUP ( ? )
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

				; Obtener el valor de Numero
				MOV		EAX, Long_P
				INVOKE	WriteConsoleA, ManejadorS, ADDR Prompt, EAX, ADDR Caracteres, 0
				INVOKE	ReadConsoleA, ManejadorE, ADDR Texto, 13, ADDR Caracteres, 0
				MacroCadenaAEntero	Texto, Numero
				
				; Determinar si es primo o no
				; Divisiones sucesivas del numero entre 2 y hasta numero - 1
				; si hay una division exacta, significa que numero es no primo
								
				MOV		ESI, 1						; ESI es indicador de Primo: 1 es verdadero, 0 es falso
				MOV		ECX, Numero
				DEC		ECX
				MOV		EBX, 2
Inicio:
				CMP		EBX, ECX
				JNL		Salir
				; Dividir y verificar el residuo
				MOV		EAX, Numero
				CDQ									; EAX -> EDX:EAX
				DIV		EBX
				CMP		EDX, 0
				JNE		Siguiente
				MOV		ESI,0						; Primo = falso
				JMP		Salir
Siguiente:
				; Preparar la siguiente iteraciòn
				INC		EBX
				JMP		Inicio
Salir:

				; Imprimir mensaje correspondiente
				CMP		ESI, 1
				JNE		No_Primo
				; Imprimir mensaje de primo
				MOV		EAX, Long_MP
				INVOKE	WriteConsoleA, ManejadorS, ADDR Men_Primo, EAX, ADDR Caracteres, 0
				JMP		Terminar
No_Primo:
				; Imprimir mensaje de no primo
				MOV		EAX, Long_MNP
				INVOKE	WriteConsoleA, ManejadorS, ADDR Men_NoPrimo, EAX, ADDR Caracteres, 0
				
Terminar:
				; Salir
				INVOKE	ExitProcess, 0								
Main			ENDP
				END Main
