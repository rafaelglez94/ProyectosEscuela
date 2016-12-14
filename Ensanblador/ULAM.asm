				TITLE	Programa para evaluar la conjetura de ULAM
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
SaltoLinea		BYTE	13,10
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

				CMP		Numero, 1
				JE		Salir
				
Inicio:				
				; Preguntar si numero es par o impar
				TEST	Numero, 1
				JNZ		Impar
				; Es par. Entonces dividir entre 2
				MOV		EAX, Numero
				CDQ						; EAX -> EDX:EAX
				MOV		EBX, 2
				IDIV	EBX
				MOV		Numero, EAX
				JMP		Imprimir
				
Impar:
				; Es impar. Multiplicar por 3 y sumarle 1
				MOV		EAX, 3
				IMUL	Numero
				MOV		Numero, EAX
				INC		Numero

Imprimir:				
				; Imprimir nùmero y salto de linea
				; Convertir numero a cadena
				MacroEnteroACadena Numero, Texto, Caracteres
				;Imprimir Numero
				INVOKE	WriteConsoleA, ManejadorS, ADDR Texto, Caracteres, ADDR Caracteres, 0
				INVOKE	WriteConsoleA, ManejadorS, ADDR SaltoLinea, 2, ADDR Caracteres, 0
				
				; Preguntar si es 1 y salir
				CMP		Numero, 1
				JNE		Inicio

Salir:
				; Terminar
				INVOKE	ExitProcess, 0								
Main			ENDP
				END Main
				