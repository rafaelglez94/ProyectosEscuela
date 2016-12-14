				TITLE	Programa para convertir de minusculas a mayusculas
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
PromptA			BYTE	"Proporcione un cadena en minusculas: "
Long_PA			EQU		$ - PromptA
Men_Sal			BYTE	"La cadena en mayusculas es: "
Long_MS			EQU		$ - Men_Sal
SaltoLinea		BYTE	13,10
Texto			BYTE	80 DUP ( ? )
Caracteres		DWORD	?
ManejadorE		DWORD	?
ManejadorS		DWORD	?

				.CODE
Main			PROC
				; Obtener manejadores de entrada y salida
				INVOKE	GetStdHandle, STD_INPUT_HANDLE
				MOV		ManejadorE, EAX
				INVOKE	GetStdHandle, STD_OUTPUT_HANDLE
				MOV		ManejadorS, EAX

				; Obtener la cadena en minusculas
				MOV		EAX, Long_PA
				INVOKE	WriteConsoleA, ManejadorS, ADDR PromptA, EAX, ADDR Caracteres, 0
				INVOKE	ReadConsoleA, ManejadorE, ADDR Texto, 80, ADDR Caracteres, 0
				
				; Ajustar la longitud de la cadena
				SUB		Caracteres, 2					; Para quitar el 13, 10
				
				; Ciclo por la longitud de la cadena
				MOV		ECX, Caracteres
				MOV		ESI, 0							; indice de la cadena
Inicio:
				; Preguntar si es caracter en minuscula
				CMP		Texto[ESI], 'a'
				JB		Continuar
				CMP		Texto[ESI], 'z'
				JA		Continuar
				
				; Es minuscula
				AND		Texto[ESI], 11011111B			; Restar 32 al caracter para hacerlo mayuscula

Continuar:
				; Incrementar indice
				INC		ESI
				LOOP	Inicio
				
				; Imprimir la conversion
				MOV		EAX, Long_MS
				INVOKE	WriteConsoleA, ManejadorS, ADDR Men_Sal, EAX, ADDR Caracteres, 0				
				INVOKE	WriteConsoleA, ManejadorS, ADDR Texto, Caracteres, ADDR Caracteres, 0
				; Salto de linea
				INVOKE	WriteConsoleA, ManejadorS, ADDR SaltoLinea, 2, ADDR Caracteres, 0
				; Salir
				INVOKE	ExitProcess, 0								
Main			ENDP
				END Main
				