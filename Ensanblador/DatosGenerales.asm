Title Datos Generales De Alumnos
.586
.MODEL FLAT,STDCALL

;Constantes de consola
STD_OUTPUT_HANDLE EQU -11

;Prototipos de funciones a utilizar
GetStdHandle	PROTO 	:DWORD
WriteConsoleA	PROTO 	:DWORD, :DWORD, :DWORD, :DWORD, :DWORD
ExitProcess 	PROTO 	:DWORD

	.STACK
	.DATA
InstCarr		BYTE 	"____________Institucion:______________", 13,10,"---Instituto Tecnologico de Culiacan--", 13,10,"______________Carrera:________________", 13,10,"Ingenieria en Sistemas Computacionales", 13, 10
Long_I		EQU  	$-InstCarr
SemMat			BYTE	"_____________Semestre:________________", 13,10,"-----------Sexta Entrada--------------", 13, 10,"______________Materia:________________", 13,10,"-------Leguajes de Interface----------", 13, 10
Long_S		EQU  	$-SemMat
Alumnos			BYTE	"______________Alumnos:________________", 13,10,"-Veronica Guadalupe Valuenzuela Sosa--", 13, 10,"------Rafael Gonzalez Castro----------", 13, 10,10					
Long_A		EQU  	$-Alumnos

Manejador 	DWORD 	?
Caracteres 	DWORD 	?

	.CODE
Main	PROC
		; Institucion Y Carrera
		INVOKE 	GetStdHandle, STD_OUTPUT_HANDLE
		MOV 	Manejador, EAX
		MOV 	EAX, Long_I
		INVOKE 	WriteConsoleA, Manejador, ADDR InstCarr, EAX, ADDR Caracteres, 0
		
		; Semestre Y Materia
		MOV 	EAX, Long_S
		INVOKE 	WriteConsoleA, Manejador, ADDR SemMat, EAX, ADDR Caracteres, 0
		
		;Alumnos
		MOV 	EAX, Long_A
		INVOKE 	WriteConsoleA, Manejador, ADDR Alumnos, EAX, ADDR Caracteres, 0
		INVOKE 	ExitProcess, 0 

Main	ENDP
			END 	Main