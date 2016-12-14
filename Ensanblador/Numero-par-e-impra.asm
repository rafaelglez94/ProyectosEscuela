					TITLE	Programa para leer 10 numeros y contar pares e impares
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
Vector				DWORD	10 DUP ( ? )
Pares				DWORD	0
Impares				DWORD	0
Prompt				BYTE	"Proporcione un numero: "
Long_P1				EQU		$ - Prompt
MenPares			BYTE	"Total de pares: "
Long_Par			EQU		$ - MenPares
MenImpares			BYTE	"Total de impares: "
Long_Impar			EQU		$ - MenImpares
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
					
					; Ciclo para leer 10 nùmeros
					MOV		ECX, 10
					MOV		ESI, 0
					
INICIO:
					;Salvar ECX
					PUSH	ECX
					
					MOV		EAX, Long_P1
					INVOKE	WriteConsoleA, ManejadorS, ADDR Prompt, EAX, ADDR Caracteres, 0
					INVOKE	ReadConsoleA, ManejadorE, ADDR Texto, 13, ADDR Caracteres, 0
					SUB		Caracteres, 2
					
					; Recuperar ECX
					POP		ECX
					
					; Convertir Texto a Numero
					MacroCadenaAEntero	Texto, Numero
					
					; Guardar numero en el vector
					MOV		EAX, Numero
					MOV		Vector[ESI], EAX
					
					; Actualizar indice y contador
					ADD		ESI, 4
					DEC		ECX
					JNZ		INICIO
					
					
					; Ciclo para procesar los 10 numeros
					MOV		ECX, 10
					MOV		ESI, 0
					MOV		EBX, 2
PROCESAR:
					MOV		EAX, Vector[ESI]
					CDQ						; EAX -> EDX:EAX
					IDIV	EBX
					
					; Preguntar si el residuo es 0
					CMP		EDX, 0
					JNE		IMPAR
PAR:				
					INC		Pares
					JMP		CONTINUAR
IMPAR:				
					INC		Impares
CONTINUAR:
					ADD		ESI, 4
					DEC		ECX
					JNZ		PROCESAR
					
					; Mostrar resultados
					MOV		EAX, Long_Par
					INVOKE	WriteConsoleA, ManejadorS, ADDR MenPares, EAX, ADDR Caracteres,0
					; Convertir de numero a texto					
					MacroEnteroACadena Pares, Texto, Caracteres
					INVOKE	WriteConsoleA, ManejadorS, ADDR Texto, Caracteres, ADDR Caracteres, 0

					; Salto de Linea
					INVOKE	WriteConsoleA, ManejadorS, ADDR SaltoDeLinea, 2, ADDR Caracteres, 0
					
					MOV		EAX, Long_Impar
					INVOKE	WriteConsoleA, ManejadorS, ADDR MenImpares, EAX, ADDR Caracteres,0
					; Convertir de numero a texto					
					MacroEnteroACadena Impares, Texto, Caracteres
					INVOKE	WriteConsoleA, ManejadorS, ADDR Texto, Caracteres, ADDR Caracteres, 0

					; Salto de Linea
					INVOKE	WriteConsoleA, ManejadorS, ADDR SaltoDeLinea, 2, ADDR Caracteres, 0

					; Salir al SO
					INVOKE	ExitProcess, 0					
MAIN				ENDP
					END		MAIN