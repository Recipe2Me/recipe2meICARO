/*la ultima vez que estuviste/accediste/viniste elegiste/decidiste/quisiste preparar/hacer/cocinar la receta receta*/
/*Acordarse de pasar la receta entre corchetes, separado por comas y en minuscula*/

frase(F, Receta) :- ppio(P), subord(S), verbo1(V1i), conjuga(V1i, tu, V1), verbo2(V2), cd(C), F = [P, S, V1, V2, C, Receta].
frase(F, Receta) :- ppio(P), subord(S), verbo1(V1i), conjuga(V1i, tu, V1), cd(C), F = [P, S, V1, C, Receta] .
frase(F, Receta) :- ppio(P), verbo1(V1i), conjuga(V1i, tu, V1), cd(C), F = [P, V1, C, Receta] .
frase(F, Receta) :- ppio(P), verbo1(V1i), conjuga(V1i, tu, V1), F = [P, V1, Receta] .
frase(F, Receta) :- ppio(P), verbo1(V1i), conjuga(V1i, tu, V1), verbo2(V2), cd(C), F = [P, V1, V2, C, Receta] .
frase(F, Receta) :- ppio(P), verbo1(V1i), conjuga(V1i, tu, V1), verbo2(V2), F = [P, V1, V2, Receta] .

ppio([la, ultima, vez]).
ppio([el, otro, dia]).

subord(S) :- verboS(Vi), conjuga(Vi, tu, V), S = [que, V].

verboS(estar).
verboS(acceder).
verboS(venir).

verbo1(elegir).
verbo1(decidir).
verbo1(querer).

verbo2(preparar).
verbo2(hacer).
verbo2(cocinar).

conjuga(estar, tu, estuviste).
conjuga(acceder, tu, accediste).
conjuga(venir, tu, viniste).
conjuga(elegir, tu, elegiste).
conjuga(decidir, tu, decidiste).
conjuga(querer, tu, quisiste).

cd([la, receta]).
cd([el, plato]).