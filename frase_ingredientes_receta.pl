frase(F) :- ppio(P), verbo1(Vp), conjuga(Vp, tu, V1), subord(S), F = [P, V1, los, ingredientes, S].
frase(F) :- verbo1(Vp), conjuga(Vp, tu, V1), subord(S), F = [V1, los, ingredientes, S].

subord(S) :- verbo2(V2p), conjuga(V2p, tu, V2), subord2(S2), S = [que, V2, S2].
subord(S) :- verbo2(V2p), conjuga(V2p, tu, V2), S = [que, V2].

subord2(S2) :- verbo3(V3p), conjuga(V3p, ellos, V3), cd(C), S2 = [que, V3, en, C].
subord2(S2) :- verbo3(V3p), conjuga(V3p, ellos, V3), S2 = [que, V3].

ppio([por, favor]).
ppio([si, noo, es, molestia]).
ppio([si, noo, te, importa]).

verbo1(decir).
verbo1(listar).
verbo1(enumerar).
verbo1(dar).
verbo1(proporcionar).

verbo2(querer).
verbo2(desear).

verbo3(aparecer).
verbo3(estar).

conjuga(decir, tu, dime).
conjuga(listar, tu, listame).
conjuga(enumerar, tu, enumerame).
conjuga(dar, tu, dame).
conjuga(proporcionar, tu, proporcioname).
conjuga(querer, tu, quieres).
conjuga(desear, tu, deseas).
conjuga(aparecer, ellos, aparezcan).
conjuga(estar, ellos, esten).

cd([la, receta]).
cd([el, plato]).