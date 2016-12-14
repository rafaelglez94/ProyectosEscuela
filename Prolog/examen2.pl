%suma lista
sumaLista([],0).
sumaLista([X|R],Z):-sumaLista(R,Y),Z is Y + X.



creaLista(_,[],[]).
creaLista(B,[X|LIR],[X|LOR]):-B > X , creaLista(B,LIR,LOR).
creaLista(B,[X|LIR],LOR):-creaLista(B,LIR,LOR).



contar(X,[],0).
contar(X,[X|L],C):- !,contar(X,L,C1), C is C1+1.
contar(X,[Y|L],C):- contar(X,L,C).

removerElemento(_, [], []).
removerElemento(Y, [Y|Xs], Zs):-removerElemento(Y, Xs, Zs).
removerElemento(X, [Y|Xs], [Y|Zs]):-removerElemento(X, Xs, Zs).

concatenar([], Cs, Cs).
concatenar([A|As],Bs,[A|Cs]):-concatenar(As, Bs, Cs).

numerodeveces([],[]).
numerodeveces([X|R],S):- contar(X,[X|R],A),W = [X,A],removerElemento(X,R,C),numerodeveces(C,Z),concatenar(Z,[W],S).
