					TITLE	Programa para leer 1 numero y desplegarlo en binario
					.586
					.MODEL	FLAT, STDCALL
					
; Prototipos de funciones a utilizar
GetStdHandle		PROTO	:DWORD
ReadConsoleA		PROTO	:DWORD,	:DWORD,	:DWORD,	:DWORD,	:DWORD
WriteConsoleA		PROTO	:DWORD,	:DWORD,	:DWORD,	:DWORD,	:DWORD
ExitProcess			PROTO	:DWORD

INCLUDE <Macros.inc>

					.STACK
					.DATA
STD_INPUT_HANDLE	EQU		-10
STD_OUTPUT_HANDLE	EQU		-11
Prompt				BYTE	"Proporcione un numero: "
Long_P1				EQU		$ - Prompt
MenSal				BYTE	"La representacion en binario es: "
Long_Sal			EQU		$ - MenSal
ManejadorE			DWORD	0
ManejadorS			DWORD	0
Numero				DWORD	0
Texto				BYTE	13 DUP ( ? )
Caracteres			DWORD	?
SaltoDeLinea		BYTE	13, 10
DesplegarCero		BYTE	"0"
DesplegarUno		BYTE	"1"

					.CODE
MAIN				PROC
					; Obtner manejadores de Entrada y Salida
					INVOKE	GetStdHandle, STD_INPUT_HANDLE
					MOV		ManejadorE, EAX
					INVOKE	GetStdHandle, STD_OUTPUT_HANDLE
					MOV		ManejadorS, EAX
MIENTRAS:					
					INVOKE	WriteConsoleA, ManejadorS, ADDR SaltoDeLinea, 2, ADDR Caracteres, 0
					MOV		EAX, Long_P1
					INVOKE	WriteConsoleA, ManejadorS, ADDR Prompt, EAX, ADDR Caracteres, 0
					INVOKE	ReadConsoleA, ManejadorE, ADDR Texto, 13, ADDR Caracteres, 0
					; Convertir Texto a Numero
					MacroCadenaAEntero	Texto, Numero
					
					CMP		Numero, 0
					JE		Salir
					MOV		EAX, Long_Sal
					INVOKE	WriteConsoleA, ManejadorS, ADDR MenSal, EAX, ADDR Caracteres, 0

					MOV		ECX, 32
					MOV		EAX, Numero
Inicio:
					PUSH	ECX
					SHL		EAX, 1
					PUSH	EAX
					JC		Imprimir_1
					INVOKE	WriteConsoleA, ManejadorS, ADDR DesplegarCero, 1, ADDR Caracteres, 0
					JMP		Continuar
Imprimir_1:	
					INVOKE	WriteConsoleA, ManejadorS, ADDR DesplegarUno, 1, ADDR Caracteres, 0
Continuar:
					POP		EAX
					POP		ECX
					LOOP	Inicio
					JMP		MIENTRAS
Salir:					
					
					; Salir al SO
					INVOKE	ExitProcess, 0					
MAIN				ENDP
					END		MAIN