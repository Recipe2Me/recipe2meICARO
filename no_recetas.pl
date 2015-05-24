/* no tenemos/quedan/hay recetas con los criterios/ingredientes que has introducido/introducidos/listados/enumerados*/

frase(F) :- ppio(Pr), verbo1(V1p), conjuga(V1p, _, V1), cd(C), subord(S), F = [Pr, pero, noo, V1, recetas, con, C, S].
frase(F) :- verbo1(V1p), conjuga(V1p, _, V1), cd(C), subord(S), F = [noo, V1, recetas, con, C, S].

subord(S) :- verbo(Vp), conjuga(Vp, participio, V), S = [que, has, V].
subord(S) :- verbo(Vp), conjuga(Vp, participio, Vi), atom_concat(Vi, s, S).

cd([los, ingredientes]).
cd([los, criterios]).
cd([las, especificaciones]).
cd([las, exigencias]).
cd([los, parametros]).

ppio([lo, siento]).
ppio(disculpa).
ppio(perdona).
ppio(disculpame).
ppio(perdoname).

verbo(decir).
verbo(listar).
verbo(enumerar).
verbo(dar).
verbo(proporcionar).
verbo(introducir).

verbo1(tener).
verbo1(haber).
verbo1(quedar).

conjuga(decir, participio, dicho).
conjuga(listar, participio, listado).
conjuga(enumerar, participio, enumerado).
conjuga(dar, participio, dado).
conjuga(proporcionar, participio, proporcionado).
conjuga(introducir, participio, introducido).

conjuga(tener, nosotros, tenemos).
conjuga(quedar, ellos, quedan).
conjuga(haber, el, hay).