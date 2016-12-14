				TITLE	Programa para convertir de Mayusculas a minusculas
				.686
				.MODEL	FLAT, STDCALL

;Prototipos de funciones a utilizar
GetStdHandle	PROTO	:DWORD
ReadConsoleA	PROTO	:DWORD, :DWORD, :DWORD, :DWORD, :DWORD
WriteConsoleA	PROTO	:DWORD, :DWORD, :DWORD, :DWORD, :DWORD
ExitProcess		PROTO	:DWORD

		.STACK
		.DATA
STD_INPUT_HANDLE	EQU	-10
STD_OUTPUT_HANDLE	EQU	-11
PromptA			BYTE	"Proporcione un cadena en Mayuscula: "
Long_PA			EQU		$ - PromptA
Men_Sal			BYTE	"La cadena en Minusculas es: "
Long_MS			EQU		$ - Men_Sal
SaltoLinea		BYTE	13,10
Texto			BYTE	80 DUP ( ? )
VocalesAMa		BYTE	 10110101B, 10010000B, 11010110B, 11100000B, 11101001B, 10100101B
VocalesAMi		BYTE	 10100000B, 10000010B, 10100001B, 10100010B, 10100011B, 10100100B
Caracteres		DWORD	?
ManejadorE		DWORD	?
ManejadorS		DWORD	?

				.CODE
Main			PROC
				INVOKE	GetStdHandle, STD_INPUT_HANDLE
				MOV		ManejadorE, EAX
				INVOKE	GetStdHandle, STD_OUTPUT_HANDLE
				MOV		ManejadorS, EAX
				MOV		EAX, Long_PA
				INVOKE	WriteConsoleA, ManejadorS, ADDR PromptA, EAX, ADDR Caracteres, 0
				INVOKE	ReadConsoleA, ManejadorE, ADDR Texto, 80, ADDR Caracteres, 0
				SUB		Caracteres, 2	
				MOV		ECX, Caracteres
				MOV		ESI, 0				
Inicio:
				CMP		Texto [ESI], 'A'
				JB		NoMay
				CMP		Texto[ESI], 'Z'
				JA		NoMay
				OR		Texto[ESI], 00100000B
				MOV		EBX, ESI
				JMP		Continuar
NoMay:
				MOV		AL,Texto[ESI]
				PUSH	ESI
				MOV		ESI,0
Mientras:		
				CMP		AL, VocalesAMa[ESI]
				JNE		Siguiente
				MOV		AL, VocalesAMi[ESI]
				POP		ESI
				MOV		Texto[ESI],	AL
				JMP		Continuar
Siguiente:
				INC		ESI		
				CMP		ESI, 6
				JNE		Mientras
				POP		ESI
Continuar:				
				INC		ESI
				LOOP	Inicio
				PUSH Caracteres
				MOV		EAX, Long_MS
				INVOKE	WriteConsoleA, ManejadorS, ADDR Men_Sal, EAX, ADDR Caracteres, 0				
				POP Caracteres
				INVOKE	WriteConsoleA, ManejadorS, ADDR Texto, Caracteres, ADDR Caracteres, 0
				INVOKE	WriteConsoleA, ManejadorS, ADDR SaltoLinea, 2, ADDR Caracteres, 0
				INVOKE	ExitProcess, 0								
Main			ENDP
				END Main
