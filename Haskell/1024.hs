import System.IO.Unsafe
import System.Random

type LineaV = [Int]
type Tablero = (LineaV,
                LineaV,
                LineaV,
                LineaV,Int)

obtenerPuntajeTablero:: Tablero -> Int
obtenerPuntajeTablero (x,y,z,w,p) = p
agregarPuntajeTablero:: Tablero -> Int-> Tablero
agregarPuntajeTablero (x,y,z,w,p)  a = (x,y,z,w,(p+a))

obtenerPuntajeLinea:: ([Int],Int) -> Int
obtenerPuntajeLinea (x,p) = p
obtenerListaLinea:: ([Int],Int) -> [Int]
obtenerListaLinea (x,p ) = x


sumarParaArriba::Tablero -> Tablero
sumarParaArriba ([],[],[],[],p) = ([],[],[],[],p)
sumarParaArriba ((x:xs),(y:ys),(z:zs),(w:ws),p) = concatenarLinea (convertirLinea linea)  (sumarParaArriba (xs, ys, zs, ws,p))
                                                where linea = obtenerLinea ((x:y:z:[w]),0)


sumarParaAbajo::Tablero -> Tablero
sumarParaAbajo ([],[],[],[],p) = ([],[],[],[],p)
sumarParaAbajo ((x:xs),(y:ys),(z:zs),(w:ws),p) = concatenarLinea (convertirLinea (voltearLinea linea))  (sumarParaAbajo (xs, ys, zs, ws,p))
                                                where linea = obtenerLinea (voltearLinea ((x:y:z:[w]),0))

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

voltearLinea::([Int],Int) -> ([Int],Int)
voltearLinea (x,p) = (reverse x,p)


convertirLinea:: ([Int],Int) -> ([Int],[Int],[Int],[Int],Int)
convertirLinea ((x:y:z:w),p) = ( [x] , [y] , [z] , w, p  )

concatenarLinea:: ([Int],[Int],[Int],[Int],Int) -> ([Int],[Int],[Int],[Int],Int)-> ([Int],[Int],[Int],[Int],Int)
concatenarLinea (x,y,z,w,p2) (a,b,c,d,p1) = (x++a,y++b,z++c,w++d,(p1+p2))

obtenerLinea::([Int],Int) -> ([Int],Int)
obtenerLinea x = rellenarCerosLinea (sumarLinea (quitarCerosLinea x))

quitarCerosLinea:: ([Int],Int) ->([Int],Int)
quitarCerosLinea (x,p) = ((quitarCeros x),p)

quitarCeros::[Int] -> [Int]
quitarCeros [] = []
quitarCeros (x:xs) | x == 0 = quitarCeros xs
                   | otherwise = x :quitarCeros xs

rellenarCerosLinea:: ([Int],Int) ->([Int],Int)
rellenarCerosLinea (x,p) = ((rellenarConCeros x 4),p)

rellenarConCeros::[Int] ->Int ->[Int]
rellenarConCeros x y | l < y = x ++ hacerListaCeros (y-l)
                    |l == y = x
                    where l = longitud x

hacerListaCeros::Int ->[Int]
hacerListaCeros 0 = []
hacerListaCeros x = 0:hacerListaCeros (x-1)

sumarLinea:: ([Int],Int) -> ([Int],Int)
sumarLinea ([],p) = ([],p)
sumarLinea ([x],p) = ([x],p)
sumarLinea ((x:y:xs),p) | x == y = concatenarSuma ([(x+y)],x+y) (sumarLinea (xs,p))
                        | otherwise = concatenarSuma ([x],0)  (sumarLinea ((y:xs),p))


concatenarSuma:: ([Int],Int)-> ([Int],Int) -> ([Int],Int)
concatenarSuma (x,p1)(y,p2) =  ((x++y),(p1+p2))

longitud:: [a] -> Int
longitud [] = 0
longitud (x:xs) = (longitud xs) + 1


numeroRandom::Int -> Int
numeroRandom lim = unsafePerformIO (getStdRandom (randomR (0,lim)))


ponerUnDosEnTablero::Tablero -> Int -> Int -> Tablero
ponerUnDosEnTablero (x,y,z,w,p) a b |  a == 0 = ((ponerUnDosEnLaCelda x b),y,z,w,p)
                                  |  a == 1 = (x,(ponerUnDosEnLaCelda y b),z,w,p)
                                  |  a == 2 = (x,y,(ponerUnDosEnLaCelda z b),w,p)
                                  |  a == 3 = (x,y,z,(ponerUnDosEnLaCelda w b),p)
                                  | otherwise  = (x,y,z,w,p)

ponerUnDosEnLaCelda::[Int] -> Int -> [Int]
ponerUnDosEnLaCelda [] _ = []
ponerUnDosEnLaCelda (x:xs) i | i == 0 = [2] ++ (ponerUnDosEnLaCelda xs (i-1))
                             | otherwise = [x] ++ (ponerUnDosEnLaCelda xs (i-1))

-- Checa si la celda esta disponible en la posicion a b
celdaDisponible::Tablero -> Int -> Int -> Bool
celdaDisponible (x,y,z,w,p) a b | a == 0  && x!!b /= 0 = False
                              | a == 1 && y!!b /= 0 = False
                              | a == 2 && z!!b /= 0 = False
                              | a == 3 && w!!b /= 0 = False
                              | otherwise = True

-- checa todo el  tablero para ver si hay posiciones con 0
hayCeldasDisponibles::Tablero -> Bool
hayCeldasDisponibles ([],[],[],[],p) = False
hayCeldasDisponibles (x:xs,y:ys,z:zs,w:ws,p) | x == 0 || y== 0 ||z== 0 || w== 0 = True
                                             | otherwise = hayCeldasDisponibles (xs,ys,zs,ws,p)

generarDosEnTablero:: Tablero->  Int -> Tablero
generarDosEnTablero t l | dis == True = (ponerUnDosEnTablero t x y)
                         | otherwise = (generarDosEnTablero t l)
                        where
                            dis = (celdaDisponible t x y)
                            x = numeroRandom l
                            y = numeroRandom l

generarTableroInicial:: Tablero->  Int -> Tablero
generarTableroInicial t l = generarDosEnTablero (generarDosEnTablero t l) l

-- mostrarTablero ([0,0,0,0],[0,0,0,0],[0,0,0,0],[0,0,0,0],1)

mostrarTablero::Tablero -> String
mostrarTablero (x,y,z,w,p) = "-------- P: "++show p++"\n--------------\n"++ (imprimirArreglo x) ++"\n"++ (imprimirArreglo y) ++"\n"++(imprimirArreglo z) ++"\n"++ (imprimirArreglo w) ++"\n"++ "--------------"

imprimirArreglo:: [Int] ->String
imprimirArreglo [] = ""
imprimirArreglo [x] = show x
imprimirArreglo (x:xs) =   show x ++" , "++ imprimirArreglo xs

jugar:: IO()
jugar = do
            let x = generarTableroInicial ([0,0,0,0],[0,0,0,0],[0,0,0,0],[0,0,0,0],0) 3
            juego x

juego x = do
            putStrLn (mostrarTablero x)
            putStrLn ("0.-Salir \n W.-Arriba \nS.-Abajo \nA.-Derecha \nD.-Izquierda \n")
            opt <- getLine
            if opt == "0"
                then do
                    putStrLn ("Puntaje Alcanzado:"++show (obtenerPuntajeTablero x)++"\n Bye Bye ")
                    return ()
                else
                    if hayCeldasDisponibles x == True
                        then
                            do juego (generarDosEnTablero (opcionElegida x opt) 3)
                        else
                            do
                             putStrLn ("Puntaje Alcanzado:"++show (obtenerPuntajeTablero x)++"\nNo hay mas movimientos")


opcionElegida::Tablero -> String -> Tablero
opcionElegida t opt | opt == "w"|| opt == "W" = sumarParaArriba t
                    | opt == "s"|| opt == "S" = sumarParaAbajo t
                    | opt == "d"|| opt == "D" = sumarParaDerecha t
                    | opt == "a"|| opt == "A" = sumarParaIzquierda t
                    | otherwise = t
