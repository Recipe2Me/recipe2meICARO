/* Frase de saludo para un usuario desconocido */
frase_saludo_usuario_desconocido(F) :- frase_saludo(F1), frase_presentacion(F2), F = [F1, F2].

frase_saludo([saludos, '!']).
frase_saludo([muy, buenas, '!']).
frase_saludo([que, tal, '?']).
frase_saludo([bienvenido, '!']).
frase_saludo([hola, '!']).
frase_saludo([como, estas, '?']).

/* me llamo recipe2me */
frase_presentacion(F) :- frase_comienzo(F1), frase_explicacion(F2), F = [F1, F2].

frase_comienzo(F) :- sn(S), sv_comienzo(Sv), F = [S, Sv].

sn(S) :- sujeto(S).

sujeto(yo).
sujeto('').

sv_comienzo(Sv) :- pronombre_personal(P), es_verbo_comienzo(Vp), es_verbo_pronombre(Vp), conjuga(Vp, yo, V), Sv = [P, V, recipe2me].
sv_comienzo(Sv) :- es_verbo_comienzo(Vp), \+es_verbo_pronombre(Vp), conjuga(Vp, yo, V), Sv = [V, recipe2me].

/*
me dedico a recomendar recetas
mi trabajo consiste en buscar recetas
sirvo para recomendar recetas
*/

frase_explicacion(F) :- sv_explicacion(Sv), F = [y, Sv].

sv_explicacion(Sv) :- pronombre_personal(P), es_verbo_pronombre(Vp), conjuga(Vp, yo, V), subordinada(F, Vp), Sv = [P, V, F].
sv_explicacion(Sv) :- es_verbo(Vp), es_verbo_subord(Vp), conjuga(Vp, yo, V), cd(C), Sv = [V, C].

pronombre_personal(me).

es_verbo(ser).
es_verbo(servir).
es_verbo(dedicar).
es_verbo(encargar).
es_verbo(recomendar).
es_verbo(buscar).
es_verbo(recetar).
es_verbo(aconsejar).

es_verbo_comienzo(ser).
es_verbo_comienzo(llamar).

es_verbo_pronombre(llamar).
es_verbo_pronombre(dedicar).
es_verbo_pronombre(encargar).

es_verbo_subord(recomendar).
es_verbo_subord(servir).
es_verbo_subord(encargar).
es_verbo_subord(buscar).
es_verbo_subord(recetar).
es_verbo_subord(aconsejar).

conjuga(ser, yo, soy).
conjuga(servir, yo, sirvo).
conjuga(decicar, yo, dedico).
conjuga(encargar, yo, encargo).
conjuga(recomendar, yo, recomiendo).
conjuga(buscar, yo, busco).
conjuga(recetar, yo, receto).
conjuga(aconsejar, yo, aconsejo).

/*
recomendar recetas
buscar recetas
recetar platos
aconsejar recetas
buscar platos
*/

subordinada(F, Vp) :- preposicion(P, Vp), es_verbo_subord(V), cd(C), F = [P, V, C].
/*subordinada(F, Vp) :- preposicion(P, Vp), es_verbo(V), es_verbo_subord(V), cc.*/

preposicion(a, dedicar).
preposicion(de, encargar).
preposicion(para, servir).

cd(C) :- objeto(C1), cc(C2), C = [C1, C2].

objeto(platos).
objeto(recetas).

cc(C) :- C = [de, cocina].