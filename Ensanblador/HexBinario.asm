				TITLE	Programa para convertir de hexadecimal a binario
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
PromptA			BYTE	"Proporcione un numero en hexadecimal: "
Long_PA			EQU		$ - PromptA
Men_Sal			BYTE	"El valor en decimal es: "
Long_MS			EQU		$ - Men_Sal
Error			BYTE	"Caracteres Invalidos "
Long_E			EQU		$ - Men_Sal
SaltoLinea		BYTE	13,10
Texto			BYTE	10 DUP ( ? )
Caracteres		DWORD	?
ManejadorE		DWORD	?
ManejadorS		DWORD	?
DesplegarCero	BYTE	"0"
DesplegarUno	BYTE	"1"
Numero			DWORD	0
Suma			DWORD	0
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
				INVOKE	ReadConsoleA, ManejadorE, ADDR Texto, 80, ADDR Caracteres, 0
				SUB		Caracteres, 2
				MOV		ECX, Caracteres
				MOV		ESI, 0					
Inicio:				
				MOV		EAX, 16
				IMUL	Numero
				MOV		Numero, EAX
				
				CMP		Texto[ESI], 'A'
				JB		Minuscula
				CMP		Texto[ESI], 'F'
				JA		Minuscula
				SUB		Texto[ESI], 17
				JMP		Letra
Minuscula:
				CMP		Texto[ESI], 'a'
				JB		Digito
				CMP		Texto[ESI], 'f'
				JA		Digito
				SUB		Texto[ESI], 49
Letra:				
				MOV		AL, Texto[ESI]
				AND		EAX, 0000000FH
				ADD		EAX, 10
				ADD		Numero, EAX

				JMP		Siguiente
Digito:
				CMP		Texto[ESI], '0'
				JB		Siguiente
				CMP		Texto[ESI], '9'
				JA		Siguiente		
				
				MOV		AL, Texto[ESI]
				AND		EAX, 0000000FH
				ADD		Numero, EAX
					
Siguiente:
				INC		ESI
				LOOP	Inicio				
				MacroEnteroACadena Numero, Texto, Caracteres
				INVOKE	WriteConsoleA, ManejadorS, ADDR Texto, Caracteres, ADDR Caracteres, 0
				INVOKE	WriteConsoleA, ManejadorS, ADDR SaltoLinea, 2, ADDR Caracteres, 0
				INVOKE	ExitProcess, 0								
Main			ENDP
				END Main
