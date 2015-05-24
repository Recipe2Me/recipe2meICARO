frase(F) :- ppio(P), frase1(F1), F = [P, pero, noo, F1] .
frase(F) :- frase1(F1), F = [noo, F1] .
frase(F) :- ppio(P), frase2(F2), F = [P, pero, noo, F2] .
frase(F) :- frase2(F2), F = [noo, F2] .

ppio([lo, siento]).
ppio(disculpa).
ppio(perdona).
ppio(disculpame).
ppio(perdoname).

frase1(F) :- verbo1(Vp), conjuga(Vp, yo, V), verbo2(V2), F = [te, V, V2].
frase1(F) :- verbo2(Vp), conjuga(Vp, yo, V), F = [te, V].

frase2(F) :- verbo1(Vp), conjuga(Vp, yo, V), verbo2(V2p), atom_concat(V2p, te, V2), F = [V, V2].

verbo1(conseguir).
verbo1(lograr).

verbo2(entender).
verbo2(comprender).
verbo2(captar).

conjuga(conseguir, yo, consigo).
conjuga(lograr, yo, logro).
conjuga(entender, yo, entiendo).
conjuga(comprender, yo, comprendo).
conjuga(captar, yo, capto).