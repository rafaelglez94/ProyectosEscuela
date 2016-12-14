{- 1)
El triángulo aritmético es:
1
2 3
4 5 6
7 8 9 10
11 12 13 14 15

Definir la función triangulo tal que (triangulo n) es el triángulo aritmético de altura n. Por ejemplo:
	triangulo 3 == [[1],[2,3],[4,5,6]]
	triangulo 4 == [[1],[2,3],[4,5,6],[7,8,9,10]]
-}

sumarHasta:: Int -> Int
sumarHasta n = sum [1..n]

desdeHastaN:: Int -> [Int]
desdeHastaN n = [sumarHasta (n-1)+1..sumarHasta n]

triangulo:: Int -> [[Int]]
triangulo n = [desdeHastaN m | m <- [1..n]]

{- 2)
 Definir la función mcd :: Integer -> Integer -> Integer, tal que (mcd a b) es el máximo común divisor de a y b calculado mediante el algoritmo de Euclides. Por ejemplo, mcd 30 45 == 15
-}

mcd:: Integer -> Integer -> Integer
mcd a b | b == 0  = a
        | b >= 0  = mcd b (mod a b)

{- 3)
	Define la función posición tal que (posicion x xs) es la primera
	posición del elemento x en la lista ys (empezando a contar por 1)
	o bien 0 si x no pertenece a lista xs. Por ejemplo:

	posicion 5 [1,5,3,5,6,5,3,4] => 2
	posicion 'l' "Haskell"       => 6
	posicion 7 [1,5,3,5,6,5,3,4] => 0

	Da una definición por recursión y otra usando listas de comprensión.
-}
-- 1
posicion'M::Int ->[Int] ->Int
posicion'M x xs = (xs !! x) + 1

-- 2
posicion:: Integer -> [Integer] -> Integer
posicion _ [] = 0
posicion x (y:xs)
                | x == y = 1
                | x /= y = (posicion x xs) + 1
-- 3
posicion'::Integer ->[Integer] ->Integer
posicion' _ [] = 0
posicion' x xs = posi x 0 xs


posi::Integer -> Integer ->[Integer] ->Integer
posi _ _ [] = 0
posi x i (y:xs)
            | x == y = i + 1
            | x /= y = posi x (i+1) xs

{- 4)
    Defina una función recursiva que nos diga cual es el incremento que se tiene en una sucesión de números de una lista, y que
    en caso de que no todos los elementos de la lista sean una sucesión nos regrese 0. Ejemplo

    sucesion [1,2,3,4]     => 1
    sucesion [11,22,33,41] => 0
    sucesion [21,28,35,42] => 7
-}

difList:: Integer -> Integer -> [Integer] -> Bool
difList _ _ [] = False
difList x d (y:xs)
                | (y - x) == d && xs == []  = True
                | (y - x) == d              = difList y d xs
                | otherwise                 = False

sucesion:: [Integer] -> Integer
sucesion (x:y:xs)
                |  difList x (y-x) (y:xs) == True = (y-x)
                |  otherwise                 = 0

{- 4) Defina una función que nos diga el Resto o Residuo de una división utilizando sustracciones de forma recursiva. Ejemplo:

Resto 10 3  => 1
Resto 15 6  => 3
Resto 20 5  => 0
-}
resto:: Integer -> Integer -> Integer
resto x y
            | (x - y) > y = resto  (x - y) y
            | otherwise = (x - y)

{- 5) Función recursiva que devuelve el sumatorio desde un valor entero hasta otro. Ejemplo:
Sumatorio 1 3   => 6
Sumatorio 2 5   => 14
Sumatorio 23 54 => 1232
Sumatorio 20 25   => 135
-}

sumatorio:: Integer -> Integer -> Integer
sumatorio x y | x < y = (sumatorio ( x + 1 ) y) + x
              | otherwise = x


{- 6)
    Función recursiva que calcula el producto de los números que hay entre el primer y segundo argumento, ambos incluidos.
-}
producto:: Integer -> Integer -> Integer
producto x y | x < y = (sumatorio ( x + 1 ) y) * x
              | otherwise = x

{- 7)
    Función recursiva que calcula el número de cifras de un número natural.
-}

numeroCifras:: Integer -> Integer
numeroCifras x  | x < 10 = 1
           | otherwise = numeroCifras (div x 10) + 1


{- 8)
Función recursiva ocurrencias, que toma un elemento y una lista y devuelve el número de ocurrencias del elemento en la lista.
-}

repeticiones:: Integer -> [Integer] -> Integer
repeticiones _ [] = 0
repeticiones a (x:xs)
                    | a == x = (repeticiones a xs) + 1
                    | otherwise = repeticiones a xs

{- 9)
Función recursiva que comprueba si una lista está ordenada ascendentemente.
-}


mayorQueEn:: Integer -> [Integer] -> Bool
mayorQueEn a [] = True
mayorQueEn a (x:xs) | a <= x = mayorQueEn a xs
                    | otherwise = False

ordenadaAsc:: [Integer] -> Bool
ordenadaAsc [] = True
ordenadaAsc (x:xs)  | (mayorQueEn x xs) == True  = ordenadaAsc xs
                    | otherwise = False




{- 10)
-- Redefine por recursión la función
--    takeWhile :: (a -> Bool) -> [a] -> [a]
-- tal que (takeWhile p xs) es la lista de los elemento de xs hasta el
-- primero que no cumple la propiedad p. Por ejemplo,
--    takeWhile (<7) [2,3,9,4,5]  =>  [2,3]
-- recuerda darle otro nombre, ya que este es el de la función de Haskell :)
-}

takeWhile' :: (a -> Bool) -> [a] -> [a]
takeWhile' _ []= []
takeWhile' p (x:xs) | p x = [x] ++ (takeWhile' p xs)
                    | otherwise = []

