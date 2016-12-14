				TITLE	Programa para imprimir los 10 primeros numeros de Fibonnaccievaluar una expresión
				.686
				.MODEL	FLAT, STDCALL

;Prototipos de funciones a utilizar
GetStdHandle	PROTO	:DWORD
WriteConsoleA	PROTO	:DWORD, :DWORD, :DWORD, :DWORD, :DWORD
ExitProcess		PROTO	:DWORD

INCLUDE <Macros.inc>

		.STACK
		.DATA
STD_OUTPUT_HANDLE	EQU	-11
Men_Sal			BYTE	"Los 10 primeros numeros de Fibonnacci son:", 13, 10
Long_MS			EQU		$ - Men_Sal
SaltoLinea		BYTE	13, 10
Texto			BYTE	13 DUP ( ? )
Caracteres		DWORD	?
ManejadorS		DWORD	?

F				DWORD	0
F1				DWORD	1
F2				DWORD	1
				.CODE
Main			PROC
				; Obtener manejador de salida
				INVOKE	GetStdHandle, STD_OUTPUT_HANDLE
				MOV		ManejadorS, EAX
				
				; Imprimir el mensaje
				MOV		EAX, Long_MS
				INVOKE	WriteConsoleA, ManejadorS, ADDR Men_Sal, EAX, ADDR Caracteres, 0
				
				; Imprimir Fibonnaci de 1 y de 2
				MacroEnteroACadena F1, Texto, Caracteres
				;Imprimir Numero
				INVOKE	WriteConsoleA, ManejadorS, ADDR Texto, Caracteres, ADDR Caracteres, 0
				INVOKE	WriteConsoleA, ManejadorS, ADDR SaltoLinea, 2, ADDR Caracteres, 0
				
				MacroEnteroACadena F2, Texto, Caracteres
				;Imprimir Numero
				INVOKE	WriteConsoleA, ManejadorS, ADDR Texto, Caracteres, ADDR Caracteres, 0
				INVOKE	WriteConsoleA, ManejadorS, ADDR SaltoLinea, 2, ADDR Caracteres, 0
				
				; Ciclo para los 8 siguiente numeros de Fibonnacci
				MOV		ECX, 8
Inicio:
				; Calcular el siguiente Fibonnacci
				MOV		EAX, F1
				ADD		EAX, F2
				MOV		F, EAX
				
				; Salvar ECX en la pila
				PUSH	ECX
				
				; Imprimir Fibonnacci				
				MacroEnteroACadena F, Texto, Caracteres
				;Imprimir Numero
				INVOKE	WriteConsoleA, ManejadorS, ADDR Texto, Caracteres, ADDR Caracteres, 0
				INVOKE	WriteConsoleA, ManejadorS, ADDR SaltoLinea, 2, ADDR Caracteres, 0
				
				; Recuperar ECX de la pila
				POP		ECX
				
				; Arrastrar los fibonnacci anteriores
				MOV		EAX, F1
				MOV		F2,  EAX
				MOV		EAX, F
				MOV		F1, EAX
				
				; Siguiente iteraccion
				;LOOP	Inicio
				DEC		ECX
				JNZ		Inicio
				
				; Salir
				INVOKE	ExitProcess, 0								
Main			ENDP
				END Main
								
