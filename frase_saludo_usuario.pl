frase_saludo_usuario_conocido(F, User) :- frase_saludo(F1), frase_personal(F2), F = [F1, User, '!', F2].

frase_saludo(saludos).
frase_saludo([muy, buenas]).
frase_saludo([que, tal]).
frase_saludo(bienvenido).
frase_saludo(hola).
frase_saludo([como, estas]).

/*
me alegro de verte de nuevo
me alegro de volver a verte
encantado de verte de nuevo
encantado de volver a verte
*/

frase_personal(F) :- pronombre_personal(Pp), es_verbo(Vp), conjuga(Vp, me, V), final(Fn), F = [Pp, V, de, Fn].
frase_personal(F) :- es_verbo(Vp), conjuga(Vp, yo, V), final(Fn), F = [V, de, Fn].

final(F) :- es_verbo_sub(Vp), atom_concat(Vp, te, V), cc(C), F = [V, C].
final(F) :- es_verbo_frec(Vp), atom_concat(Vp, te, V), subordinada(S, Vp, without), F = [V, S].
final(F) :- es_verbo_frec(V), subordinada(S, V, with), F = [V, S].

subordinada(S, Vp, with) :- preposicion(Vp, Prep), es_verbo_sub(V2), atom_concat(V2, te, V), S = [Prep, V].
subordinada(S, Vp, without) :- preposicion(Vp, Prep), es_verbo_sub(V2), S = [Prep, V2].

es_verbo(alegrar).
es_verbo(encantar).

es_verbo_frec(volver).

es_verbo_sub(ver).
es_verbo_sub(encontrar).

pronombre_personal(me).
preposicion(volver, a).

conjuga(alegrar, me, alegro).
conjuga(encantar, me, encanta).
conjuga(encantar, yo, encantado).

cc([de, nuevo]).
cc([por, aqui]).
cc([otra, vez]).
cc([]).