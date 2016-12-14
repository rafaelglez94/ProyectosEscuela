					TITLE	Programa cuenta mayusculas, minusculas digitos y otros
							; 
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
N_Caracteres		DWORD	0
Mayusculas			DWORD	0
Minusculas			DWORD	0
Digitos				DWORD	0
Otros				DWORD	0
Prompt				BYTE	"Proporcione una cadena: "
Long_P1				EQU		$ - Prompt
Men_SCar			BYTE	"Total de Caracteres: "
Long_MSCar			EQU		$ - Men_SCar

Men_SMay			BYTE	"Total de Mayusculas: "
Long_MSMay			EQU		$ - Men_SMay
Men_SMin			BYTE	"Total de Minusculas: "
Long_MSMin			EQU		$ - Men_SMin
Men_SDig			BYTE	"Total de Digitos: "
Long_MSDig			EQU		$ - Men_SDig
Men_SO			BYTE	"Total de Otros: "
Long_MSO			EQU		$ - Men_SO

ManejadorE			DWORD	0
ManejadorS			DWORD	0

Numero				DWORD	0
Texto				BYTE	13 DUP ( ? )
Caracteres			DWORD	?
SaltoDeLinea		BYTE	13, 10

					.CODE
MAIN				PROC
					; Obtner manejadores de Entrada y Salida
					INVOKE	GetStdHandle, STD_INPUT_HANDLE
					MOV		ManejadorE, EAX
					INVOKE	GetStdHandle, STD_OUTPUT_HANDLE
					MOV		ManejadorS, EAX

					MOV		EAX, Long_P1
					INVOKE	WriteConsoleA, ManejadorS, ADDR Prompt, EAX, ADDR Caracteres, 0
					INVOKE	ReadConsoleA, ManejadorE, ADDR Texto, 80 , ADDR Caracteres, 0
					SUB		Caracteres, 2
					
					MOV		ECX, Caracteres
					MOV		ESI, 0	
Inicio:
					CMP		Texto[ESI],'A'
					JB		Digito
					CMP		Texto[ESI],'Z'
					JA		Min
					INC		Mayusculas
					JMP		Continuar
Digito:
					CMP		Texto[ESI],'0'
					JB		Otro
					CMP		Texto[ESI],'9'
					JA		Otro
					INC		Digitos
					JMP		Continuar
Min:
					CMP		Texto[ESI],'a'
					JB		Otro
					CMP		Texto[ESI],'z'
					JA		Otro
					INC		Minusculas
					JMP		Continuar
Otro:
					INC		Otros
Continuar:
					INC		ESI		
					INC		N_Caracteres				
					LOOP	Inicio

					MOV		EAX, Long_MSCar
					INVOKE	WriteConsoleA, ManejadorS, ADDR Men_SCar, EAX, ADDR Caracteres,0
					MacroEnteroACadena N_Caracteres, Texto, Caracteres
					INVOKE	WriteConsoleA, ManejadorS, ADDR Texto, Caracteres, ADDR Caracteres, 0
					INVOKE	WriteConsoleA, ManejadorS, ADDR SaltoDeLinea, 2, ADDR Caracteres, 0
					
					MOV		EAX, Long_MSMay
					INVOKE	WriteConsoleA, ManejadorS, ADDR Men_SMay, EAX, ADDR Caracteres,0
					MacroEnteroACadena Mayusculas, Texto, Caracteres
					INVOKE	WriteConsoleA, ManejadorS, ADDR Texto, Caracteres, ADDR Caracteres, 0
					INVOKE	WriteConsoleA, ManejadorS, ADDR SaltoDeLinea, 2, ADDR Caracteres, 0

					MOV		EAX, Long_MSMin
					INVOKE	WriteConsoleA, ManejadorS, ADDR Men_SMin, EAX, ADDR Caracteres,0
					MacroEnteroACadena Minusculas, Texto, Caracteres
					INVOKE	WriteConsoleA, ManejadorS, ADDR Texto, Caracteres, ADDR Caracteres, 0
					INVOKE	WriteConsoleA, ManejadorS, ADDR SaltoDeLinea, 2, ADDR Caracteres, 0

					MOV		EAX, Long_MSDig
					INVOKE	WriteConsoleA, ManejadorS, ADDR Men_SDig, EAX, ADDR Caracteres,0
					MacroEnteroACadena Digitos, Texto, Caracteres
					INVOKE	WriteConsoleA, ManejadorS, ADDR Texto, Caracteres, ADDR Caracteres, 0
					INVOKE	WriteConsoleA, ManejadorS, ADDR SaltoDeLinea, 2, ADDR Caracteres, 0
					
					MOV		EAX, Long_MSO
					INVOKE	WriteConsoleA, ManejadorS, ADDR Men_SO, EAX, ADDR Caracteres,0
					MacroEnteroACadena Otros, Texto, Caracteres
					INVOKE	WriteConsoleA, ManejadorS, ADDR Texto, Caracteres, ADDR Caracteres, 0
					INVOKE	WriteConsoleA, ManejadorS, ADDR SaltoDeLinea, 2, ADDR Caracteres, 0

					; Salir al SO
					INVOKE	ExitProcess, 0					
MAIN				ENDP
					END		MAIN