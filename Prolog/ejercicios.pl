%1.Obtener el primer elemento de una lista.
primero([X|_],X).

%2.Obtener el resto de una Lista.
resto([_|Y],Y).


%3.Calcular la longitud de una lista.
longitud([_|X],Z):- longitud(X,Y),Z is Y + 1.
longitud([],0).

%4.Escribe un predicado para eliminar un elemento de una lista.
eliminar([X|Y],X,Z):-eliminar(Y,X,Z).
eliminar([X|Y],E,[X|Z]):-eliminar(Y,X,Z).
eliminar([],E,[]).


%5.Define el predicado que devuelva el último elemento de una lista
ultimo([_|X],Z):- ultimo(X,Z).
ultimo([X],X).


%6.Define un predicado para obtener el penúltimo
penultimo([_|X],Z):- penultimo(X,Z).
penultimo([X,Y],X).


%7.Predicado para verificar si todos los elementos de una lista son iguales.
iguales([X|Y]):-iguales(Y).
iguales([X,X]).

%8.Definir la relación subconjunto(L1, L2) que verifique si L2 es subconjunto de L1.
subconjunto([FRL1|RL1],L2):-subconjunto(RL1, L2).
subconjunto(L2,L2).

%9. Escribir un predicado, que determine si es posible que una pareja de recién casados, con poco presupuesto y amantes del medio ambiente realicen su luna de miel en el Estado de Sinaloa, enlazando viajes de un lugar a otro desplazándose en burro, bici o caminando.

enBurro(mochis,guasave).
enBurro(guasave,altata).
enBurro(navolato,lasRiberas).
enBurro(navolato,escuinapa).

aPie(escuinapa,imala).
aPie(escuinapa,ahome).
aPie(lasRiberas,ahome).
aPie(lasRiberas,imala).

enBici(ahome,lasBrisas).
enBici(ahome,ayune).
enBici(imala,losAngeles).
enBici(lasBrisas,mochis).
enBici(ayune,mochis).
enBici(losAngeles,mochis).

puede(X,Y):-enBurro(X,Y).
puede(X,Y):-aPie(X,Y).
puede(X,Y):-enBici(X,Y).
viajar(X,Y):-puede(X,Y).
viajar(X,Y):-puede(X,Z),viajar(Z,Y).

viaje(X, Y, enBurro(X, Y)) :- enBurro(X, Y),puede(X, Y).
viaje(X, Y, aPie(X, Y)) :-aPie(X, Y), puede(X, Y).
viaje(X, Y, enBici(X, Y)) :-enBici(X, Y), puede(X, Y).
viaje(X, Y, enBurro(X, W, R)) :- enBurro(X, W), puede(X, W), viaje(W, Y, R).
viaje(X, Y, aPie(X, W, R)) :- aPie(X, W), puede(X, W), viaje(W, Y, R).
viaje(X, Y, enBici(X, W, R)) :- enBici(X, W),puede(X, W), viaje(W, Y, R).
