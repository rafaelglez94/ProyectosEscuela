% Numero Permitidos Para Ingresar
numeroValido(1).
numeroValido(2).
numeroValido(3).
numeroValido(4).
numeroValido(5).
numeroValido(6).
numeroValido(7).
numeroValido(8).
numeroValido(9).
numeroValido(10).
numeroValido(11).
numeroValido(12).

%Suma los valores de una lista
suma([],0).
suma([X|R],T):-suma(R,S),T is S + X.

%Validacion que un elemento X No  se repitan en la Lista Enviada
noRepetido(_,[]).
noRepetido(X,[E|R]):- X \= E, noRepetido(X,R).

%Validacion que Los elementos de la lista enviada sean unicos

listaUnica([]).
listaUnica([X|R]):-numeroValido(X),noRepetido(X,R),listaUnica(R).

% Funcion de impresion de resoltado de forma de estrella
imprimir([A,B,C,D,E,F,G,H,I,J,K,L]):-
						write('\t\t\t'),writeln(J),
						writeln('\t'),writeln('\t'),writeln('\t'),
						write(A),write('\t\t'),write(B),write('\t\t'),write(C),write('\t\t'),writeln(D),
						writeln('\t'),writeln('\t'),writeln('\t'),
						write('\t'),write(I),write('\t\t\t\t'),writeln(E),
						writeln('\t'),writeln('\t'),writeln('\t'),
						write(L),write('\t\t'),write(H),write('\t\t'),write(F),write('\t\t'),writeln(K),
						writeln('\t'),writeln('\t'),writeln('\t'),
						write('\t\t\t'),writeln(G).

%Interaciones de los elementos enviados junto con el complemento de las validaciones ateriores para formar o validar los datos enviados para su posterior respuesta

%% Funcion suma se usa para la validacion que la suma de las vertices den 26 y tambien las seis puntas

obtener([A,B,C,D,E,F,G,H,I,J,K,L]):-numeroValido(A),numeroValido(B),numeroValido(C),numeroValido(D),
									suma([A,B,C,D],26),
									numeroValido(E),numeroValido(F),numeroValido(G),
									suma([D,E,F,G],26),
									numeroValido(H),numeroValido(I),
									suma([G,H,I,A],26),
									numeroValido(J),numeroValido(K),
									suma([J,C,E,K],26),
									numeroValido(L),
									suma([K,F,H,L],26),
									suma([L,I,B,J],26),
									listaUnica([A,B,C,D,E,F,G,H,I,J,K,L]),
									suma([A,D,G,K,J,L],26),
									imprimir([A,B,C,D,E,F,G,H,I,J,K,L]).

% Validacion que muestra al jugador que aristan que envio son las correctas

aristaValida([A,B,C,D|_]):-numeroValido(A),numeroValido(B),numeroValido(C),numeroValido(D),suma([A,B,C,D],X),write("Arista #1"),write("( "),write(A),write(", "),write(B),write(", "),write(C),write(", "),write(D),writeln(" )" ),write("La suma es: "),writeln(X),X==26,writeln(" Es Correcta").
aristaValida([_,_,_,D,E,F,G|_]):-numeroValido(D),numeroValido(E),numeroValido(F),numeroValido(G),suma([D,E,F,G],X),write("Arista #2"),write("( "),write(D),write(", "),write(E),write(", "),write(F),write(", "),write(G),writeln(" )" ),write("La suma es: "),writeln(X),X==26,writeln(" Es Correcta").
aristaValida([A,_,_,_,_,_,G,H,I|_]):-numeroValido(G),numeroValido(H),numeroValido(I),numeroValido(A),suma([G,H,I,A],X),write("Arista #3"),write("( "),write(G),write(", "),write(H),write(", "),write(I),write(", "),write(A),writeln(" )" ),write("La suma es: "),writeln(X),X==26,writeln(" Es Correcta").
aristaValida([_,_,C,_,E,_,_,_,_,J,K,_]):-numeroValido(J),numeroValido(C),numeroValido(E),numeroValido(K),suma([J,C,E,K],X),write("Arista #4"),write("( "),write(J),write(", "),write(C),write(", "),write(E),write(", "),write(K),writeln(" )" ),write("La suma es: "),writeln(X),X==26,writeln(" Es Correcta").
aristaValida([_,_,_,_,_,F,_,H,_,_,K,L]):-numeroValido(K),numeroValido(F),numeroValido(H),numeroValido(L),suma([K,F,H,L],X),write("Arista #5"),write("( "),write(K),write(", "),write(F),write(", "),write(H),write(", "),write(L),writeln(" )" ),write("La suma es: "),writeln(X),X==26,writeln(" Es Correcta").
aristaValida([_,B,_,_,_,_,_,_,I,J,_,L]):-numeroValido(L),numeroValido(I),numeroValido(B),numeroValido(J),suma([L,I,B,J],X),write("Arista #6"),write("( "),write(L),write(", "),write(I),write(", "),write(B),write(", "),write(J),writeln(" )" ),write("La suma es: "),writeln(X),X==26,writeln(" Es Correcta").


% Funcion para verificar si el programa falla y si asi lo es entonces manda llamar el metodo donde muestra las aristas correctas
validarRespuesta(A):-obtener(A),!.
validarRespuesta(A):-imprimir(A),aristaValida(A),fail.

	%Funcion donde le envias una lista de Variables para su posterior definicion por el usuario.

obtenerVertices([X|L],R):-
	A is R + 1,
	write('Ingresa Vertice: '),
	write(R),
	writeln(': '),
	read(X),
	dif(X,stop),
	numeroValido(X),
	obtenerVertices(L,A).
obtenerVertices([],_).

%Funcion que es llamada cuando el usuario quiere iniciar a jugar con interacion del

estrellaMagica():-obtenerVertices([A,B,C,D,E,F,G,H,I,J,K,L],1),validarRespuesta([A,B,C,D,E,F,G,H,I,J,K,L]).


%Funciones de pruebas para el programa

%% pruebas([[A,B,C,D],[D,E,F,G],[G,H,I,A],[J,C,E,K],[K,F,H,L],[L,I,B,J]]):-obtener([A,B,C,D,E,F,G,H,I,J,K,L]).
%% pruebas([[5,B,10,9],[9,E,3,6],[6,H,11,5],[1,10,E,7],[7,3,H,12],[12,11,B,1]]).
%% pruebas([[3,B,8,11],[11,E,F,6],[6,H,12,3],[1,8,E,10],[10,F,H,9],[9,12,B,1]]).
%% pruebas([[6,2,C,8],[8,3,F,4],[4,H,I,6],[12,C,3,1],[1,F,H,5],[5,I,2,12]]).
%% pruebas([[A,3,11,D],[D,9,F,6],[6,H,11,A],[1,11,9,5],[5,F,H,12],[12,10,3,1]]).
%% pruebas([[A,8,12,D],[D,9,F,10],[10,H,4,A],[3,12,9,2],[2,F,H,11],[11,4,8,3]]).
%% pruebas([[A,9,C,10],[10,E,F,5],[5,8,I,A],[2,C,E,11],[11,F,8,L],[L,I,9,2]]).
%% pruebas([[A,9,C,6],[6,2,F,8],[8,3,11,A],[5,C,2,12],[12,F,3,L],[L,11,9,5]]).




