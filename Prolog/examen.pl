esMiembro(X,[X|_]).
esMiembro(X,[_|T]):-esMiembro(X,T).

crearLista([],T,[]).
crearLista([A|R],T,[X|S]):-esMiembro(A,T),X is A,crearLista(R,T,S).
crearLista([A|R],T,S):-crearLista(R,T,S).
