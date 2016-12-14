import System.IO.Unsafe
import System.Random

type Linea = [Int]

type Tablero = (Linea,
                Linea,
                Linea,
                Linea,Int)

-- Funcion para obtener el puntaje de un tablero enviado
obtenerPuntajeTablero:: Tablero -> Int
obtenerPuntajeTablero (x,y,z,w,p) = p

--Funcion para obtener el puntaje de una tupla enviada
obtenerPuntajeLinea:: ([Int],Int) -> Int
obtenerPuntajeLinea (x,p) = p

--Funcion para obtener la lista de una tupla enviada
obtenerListaLinea:: ([Int],Int) -> [Int]
obtenerListaLinea (x,p ) = x

-- ([2,2,4,4], [0,2,0,2], [9,2,9,4], [1,3,3,0])



-- Funcion recursiva que se va obteniendo los elementos de cada linea para sumarlos para arriba
sumarParaArriba::Tablero -> Tablero
sumarParaArriba ([],[],[],[],p) = ([],[],[],[],p)
sumarParaArriba ((x:xs),(y:ys),(z:zs),(w:ws),p) = concatenarLinea (convertirLinea linea)  (sumarParaArriba (xs, ys, zs, ws,p))
                                                where linea = obtenerLinea ((x:y:z:[w]),0)

-- Funcion recursiva que se va obteniendo los elementos de cada linea para sumarlos para abajo
sumarParaAbajo::Tablero -> Tablero
sumarParaAbajo ([],[],[],[],p) = ([],[],[],[],p)
sumarParaAbajo ((x:xs),(y:ys),(z:zs),(w:ws),p) = concatenarLinea (convertirLinea (voltearLinea linea))  (sumarParaAbajo (xs, ys, zs, ws,p))
                                                where linea = obtenerLinea (voltearLinea ((x:y:z:[w]),0))

-- Funcion para sumar lineas horizontales del tablero de derecha a izquierda
sumarParaIzquierda::Tablero -> Tablero
sumarParaIzquierda (x,y,z,w,p) = (obtenerListaLinea l1,
                                  obtenerListaLinea l2,
                                  obtenerListaLinea l3,
                                  obtenerListaLinea l4,
                                  ((obtenerPuntajeLinea l1)+(obtenerPuntajeLinea l2)+(obtenerPuntajeLinea l3)+(obtenerPuntajeLinea l4)+p))
                                 where
                                    l1 = obtenerLinea (x,0)
                                    l2 = obtenerLinea (y,0)
                                    l3 = obtenerLinea (z,0)
                                    l4 = obtenerLinea (w,0)

-- Funcion para sumar lineas horizontales del tablero de izquierda a derecha
sumarParaDerecha::Tablero -> Tablero
sumarParaDerecha (x,y,z,w,p) = (  obtenerListaLinea (voltearLinea(l1)),
                                  obtenerListaLinea (voltearLinea(l2)),
                                  obtenerListaLinea (voltearLinea(l3)),
                                  obtenerListaLinea (voltearLinea(l4)),
                                    ((obtenerPuntajeLinea l1)+(obtenerPuntajeLinea l2)+(obtenerPuntajeLinea l3)+(obtenerPuntajeLinea l4)+p))
                                 where
                                    l1 = obtenerLinea (voltearLinea(x,0))
                                    l2 = obtenerLinea (voltearLinea(y,0))
                                    l3 = obtenerLinea (voltearLinea(z,0))
                                    l4 = obtenerLinea (voltearLinea(w,0))

--Manda una tupla con una liste de elementos y un entero(puntaje) y la lista la voltea para manipularla de manera mas facil
-- Esta se usa en sumarParaAbajo y sumarParaDeracha
voltearLinea::([Int],Int) -> ([Int],Int)
voltearLinea (x,p) = (reverse x,p)


-- Obtiene una tupla con una linea y un puntaje y lo conviernte a 4 listas con un puntaje para la concateniacion
-- Esta se usa en la sumarParaArriba y sumarParaAbajo
convertirLinea:: ([Int],Int) -> ([Int],[Int],[Int],[Int],Int)
convertirLinea ((x:y:z:w),p) = ( [x] , [y] , [z] , w, p  )

-- Esta se usa en la sumarParaArriba y sumarParaAbajo
-- Manda dos lineas las cuales concatea los elementos paralelos de la otra contrario y por ultimo suma su puntajes y regresa el producto de la concatenacion y la suma de puntaje
concatenarLinea:: ([Int],[Int],[Int],[Int],Int) -> ([Int],[Int],[Int],[Int],Int)-> ([Int],[Int],[Int],[Int],Int)
concatenarLinea (x,y,z,w,p2) (a,b,c,d,p1) = (x++a,y++b,z++c,w++d,(p1+p2))

-- Esta funcion primero manda llamar a quitarCerosLinea despues de lo obtenido, llama a sumarLinea que suma los elementos iguales de la lista y por ultimo rellana con ceros la lista para obtener una longitud deseada
obtenerLinea::([Int],Int) -> ([Int],Int)
obtenerLinea x = rellenarCerosLinea (sumarLinea (quitarCerosLinea x))

-- Envia una tupla la cual obtiene una lista de elementos y un elemento entero, en esta llamamos al quitarCeros enviandole solamente la lista de la tupla
quitarCerosLinea:: ([Int],Int) ->([Int],Int)
quitarCerosLinea (x,p) = ((quitarCeros x),p)

-- Envia un lista que puede obtener ceros los cuales seran removidos de esta lista y regresando la lista sin elementos con valor de cero
quitarCeros::[Int] -> [Int]
quitarCeros [] = []
quitarCeros (x:xs) | x == 0 = quitarCeros xs
                   | otherwise = x :quitarCeros xs


-- Manda una tupla de una lista de elementos con un valor entero(puntuacion) y envia la lista a rellenarla con ceros para regresarla de una longitud deseada
rellenarCerosLinea:: ([Int],Int) ->([Int],Int)
rellenarCerosLinea (x,p) = ((rellenarConCeros x 4),p)


-- manda una lista de enteros y una longitud deseada para conpletar la longitud del arreglo con ceros
rellenarConCeros::[Int] ->Int ->[Int]
rellenarConCeros x y | l < y = x ++ hacerListaCeros (y-l)
                    |l == y = x
                    where l = longitud x

-- Funcion llamada por rellenarConCeros para la creacion de listas de ceros de X tamaño de longitud
hacerListaCeros::Int ->[Int]
hacerListaCeros 0 = []
hacerListaCeros x = 0:hacerListaCeros (x-1)

-- Enviamos una lista y suma el primer elemento con el segundo si estos son iguales, en caso contrario manda recursivamente sumar linea hasta llegar a limite y despues concatenar sus retornos
sumarLinea:: ([Int],Int) -> ([Int],Int)
sumarLinea ([],p) = ([],p)
sumarLinea ([x],p) = ([x],p)
sumarLinea ((x:y:xs),p) | x == y = concatenarSuma ([(x+y)],x+y) (sumarLinea (xs,p))
                        | otherwise = concatenarSuma ([x],0)  (sumarLinea ((y:xs),p))


-- Funcion que se le envia 2 tuplas las cuales regresa la concatenacion de la lista de las tuplas y la suma de sus puntajes
concatenarSuma:: ([Int],Int)-> ([Int],Int) -> ([Int],Int)
concatenarSuma (x,p1)(y,p2) =  ((x++y),(p1+p2))


--Funcion para la Longitud de un arreglo enviado
longitud:: [a] -> Int
longitud [] = 0
longitud (x:xs) = (longitud xs) + 1


-- Generacion de un numero aleatorio de un rango de 0 hasta el valor enviado
numeroRandom::Int -> Int
numeroRandom lim = unsafePerformIO (getStdRandom (randomR (0,lim)))


-- Manda una posicion "linea, posicion linea" donde dependiendo de la linea enviada mandamos llamar a ponerUnDosEnLaCelda
ponerUnDosEnTablero::Tablero -> Int -> Int -> Tablero
ponerUnDosEnTablero (x,y,z,w,p) a b |  a == 0 = ((ponerUnDosEnLaCelda x b),y,z,w,p)
                                  |  a == 1 = (x,(ponerUnDosEnLaCelda y b),z,w,p)
                                  |  a == 2 = (x,y,(ponerUnDosEnLaCelda z b),w,p)
                                  |  a == 3 = (x,y,z,(ponerUnDosEnLaCelda w b),p)
                                  | otherwise  = (x,y,z,w,p)

-- Funcion que le mandamos una linea y una posicion , la cual ingresa un 2 en la posicion que le mandamos y la regresala linea ya actualizada
ponerUnDosEnLaCelda::[Int] -> Int -> [Int]
ponerUnDosEnLaCelda [] _ = []
ponerUnDosEnLaCelda (x:xs) i | i == 0 = [2] ++ (ponerUnDosEnLaCelda xs (i-1))
                             | otherwise = [x] ++ (ponerUnDosEnLaCelda xs (i-1))

-- Mandamos el tablero y una posicion "linea, posicion de linea" donde checa si esa posicion esta disponible preguntando si el valor de la celda es diferente a 0
celdaDisponible::Tablero -> Int -> Int -> Bool
celdaDisponible (x,y,z,w,p) a b | a == 0  && x!!b /= 0 = False
                              | a == 1 && y!!b /= 0 = False
                              | a == 2 && z!!b /= 0 = False
                              | a == 3 && w!!b /= 0 = False
                              | otherwise = True


-- Funcion para preguntar si hay una posicion del arreglo con valor de 0 para seguir jugando
hayCeldasDisponibles::Tablero -> Bool
hayCeldasDisponibles ([],[],[],[],p) = False
hayCeldasDisponibles (x:xs,y:ys,z:zs,w:ws,p) | x == 0 || y== 0 ||z== 0 || w== 0 = True
                                             | otherwise = hayCeldasDisponibles (xs,ys,zs,ws,p)

ganaste::Tablero -> Bool
ganaste (_,_,_,_,p) | p >= 1024 = True
                    | otherwise = False

-- Del tablero que le mandamos le damos una lista random y una posicion de la lista aleatorio y manda checar que esté este vacio si asi lo es manda llenar con un dos esa posicion
generarDosEnTablero:: Tablero->  Int -> Tablero
generarDosEnTablero t l | dis == True = (ponerUnDosEnTablero t x y)
                         | otherwise = (generarDosEnTablero t l)
                        where
                            dis = (celdaDisponible t x y)
                            x = numeroRandom l
                            y = numeroRandom l

-- Función donde le mandamos el tablero vacio y rellenemos con 2 dos iniciales para el inicio del juego
generarTableroInicial:: Tablero->  Int -> Tablero
generarTableroInicial t l = generarDosEnTablero (generarDosEnTablero t l) l

-- -- ([0,0,0,0],[0,0,0,0],[0,0,0,0],[0,0,0,0])

-- Función para obtener un String del tablero para mostrarlo en la funcion juego
mostrarTablero::Tablero -> String
mostrarTablero (x,y,z,w,p) = "-------- P: "++show p++"\n--------------\n"++ (imprimirArreglo x) ++"\n"++ (imprimirArreglo y) ++"\n"++(imprimirArreglo z) ++"\n"++ (imprimirArreglo w) ++"\n"++ "--------------"

-- Función para imprimir una linea del tablero en especifico
imprimirArreglo:: [Int] ->String
imprimirArreglo [] = ""
imprimirArreglo [x] = show x
imprimirArreglo (x:xs) =   show x ++" , "++ imprimirArreglo xs

-- Funcion para iniciar el juego
jugar:: IO()
jugar = do
            let x = generarTableroInicial ([0,0,0,0],[0,0,0,0],[0,0,0,0],[0,0,0,0],0) 3
            juego x
-- Funcion recursiva que se ejecuta hasta que el usuario le de la opcion de salir o que no haya mas movimientos
juego x = do
            putStrLn (mostrarTablero x)
            putStrLn ("0.-Salir \nW.-Arriba \nS.-Abajo \nA.-Izquierda \nD.-Derecha \n")
            opt <- getLine
            opcionSeleccionada opt x

-- Valida la opcion elegida por el usuario, primero pregunta si la opcion enviada fue el numero cero, si asi es ahi acaba el juego
-- Despues pregunta si en el tablero hay celdas disponibles si no hay muestra el puntaje y termina el juego
-- En la siguiente pregunta si el usuario en el movimiento anterior llego a tener un puntaje mayor a 1024 si asi lo es muestra el puntaje y termina el juego
-- Despues pregunta si la opcion seleccionada por el usuario es valida si asi lo entonces continua para la realizacion del movimiento
-- Por ultimo es el caso en que las condiciones anteriores no se cumpleran muestra al usuario que la opcion enviada es invalida y le pregunta de nuevo

opcionSeleccionada:: String -> Tablero -> IO()
opcionSeleccionada ['0'] t  = do putStrLn ("Puntaje Alcanzado:"++show (obtenerPuntajeTablero t)++"\n Bye Bye ")
opcionSeleccionada opt t | hayCeldasDisponibles t == False =   do putStrLn ("Puntaje Alcanzado:"++show (obtenerPuntajeTablero t)++"\nNo hay mas movimientos")
                         | ganaste t == True = do putStrLn ("Puntaje Alcanzado:"++show (obtenerPuntajeTablero t)++"\n Sobrepasaste 1024 :)")
                         | opcionValida opt == True = juego (generarDosEnTablero (opcionElegida t opt) 3)
                         | otherwise =  do
                                          putStrLn ("\nOpcion Invalida. Intente de nuevo\n")
                                          juego t
-- Manda la opción que el usuario mando y dependiendo de ella se llama a la funcion correspondiente
opcionValida::String-> Bool
opcionValida opt    | opt == "w"|| opt == "W" = True
                    | opt == "s"|| opt == "S" = True
                    | opt == "d"|| opt == "D" = True
                    | opt == "a"|| opt == "A" = True
                    | otherwise = False

opcionElegida::Tablero -> String -> Tablero
opcionElegida t opt | opt == "w"|| opt == "W" = sumarParaArriba t
                    | opt == "s"|| opt == "S" = sumarParaAbajo t
                    | opt == "d"|| opt == "D" = sumarParaDerecha t
                    | opt == "a"|| opt == "A" = sumarParaIzquierda t
                    | otherwise = t
