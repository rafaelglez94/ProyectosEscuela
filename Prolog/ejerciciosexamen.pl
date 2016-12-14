%1-Reemplaza (en todos los casos) la aparición de un elemento x por otro y en una lista. El predicado se definiría: reemplaza(X, Y, ListaEntrada, ListaRespuesta).
reemplazar(_,_,[],[]).
reemplazar(B,R,[B|LIR],[R|LOR]):-reemplazar(B,R,LIR,LOR).
reemplazar(B,R,[X|LIR],[X|LOR]):-reemplazar(B,R,LIR,LOR).

%2-Calcula el mayor elemento de una lista, maximo(Lista,Respuesta).
maximo([X],X).
maximo([X|LIR],X):-maximo(LIR,LOR), X > LOR.
maximo([X|LIR],LOR):-maximo(LIR,LOR), X < LOR.

%3. Determina si dos listas son iguales, compara(L1,L2).
compara([],[]).
compara([X|R],[X|Y]):-compara(R,Y).

%5. Arma una lista con todos los elementos menores que el elemento X
creaLista(_,[],[]).
creaLista(B,[X|LIR],[X|LOR]):-B > X , creaLista(B,LIR,LOR).
creaLista(B,[X|LIR],LOR):-creaLista(B,LIR,LOR).

%6. Obtener una lista de los elementos menores que X y otra con los elementos mayores que X
crea2Lista(_,[],[],[]).
crea2Lista(B,[X|LIR],[X|MEN],MAY):-B > X , crea2Lista(B,LIR,MEN,MAY).
crea2Lista(B,[X|LIR],MEN,[X|MAY]):-B < X , crea2Lista(B,LIR,MEN,MAY).

