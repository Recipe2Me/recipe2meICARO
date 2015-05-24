/*por favor/si no es molestia indicame/dime/listame/enumerame/introduce/vuelve a introducir nuevos/otros ingredientes*/

frase(F) :- ppio(P), verbo(V1p), conjuga(V1p, tu, V1), nex(N), F = [P, V1, N, ingredientes].
frase(F) :- verbo(V1p), conjuga(V1p, tu, V1), nex(N), F = [V1, N, ingredientes].
frase(F) :- ppio(P), verbo(V1), nex(N), F = [P, vuelve, a, V1, N, ingredientes].
frase(F) :- verbo(V1), nex(N), F = [vuelve, a, V1, N, ingredientes].

nex(nuevos).
nex(otros).
nex(diferentes).
nex(distintos).

ppio([por, favor]).
ppio([si, noo, es, molestia]).
ppio([si, noo, te, importa]).

verbo(decir).
verbo(listar).
verbo(enumerar).
verbo(dar).
verbo(proporcionar).
verbo(introducir).

conjuga(decir, tu, dime).
conjuga(listar, tu, listame).
conjuga(enumerar, tu, enumerame).
conjuga(dar, tu, dame).
conjuga(proporcionar, tu, proporcioname).
conjuga(introducir, tu, introduceme).