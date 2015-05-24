frase_pregunta_formulario(F, Objeto) :- sn(S), sv(S, V, Objeto), append(S, V, F).

sn(S) :- sujeto(S).

sujeto([]).
sujeto([tu]).

sv(S, V, Objeto) :- pronombre_personal(S, P), verbo(Vb), cd(C, Objeto), V = [P, Vb, C].
sv(S, V, Objeto) :- pronombre_personal(S, P), objeto(G, N, Objeto), determinante(G, N, Det), verbo(Vb), V = [P, Det, Vb].

pronombre_personal([tu], me).
pronombre_personal([], me).

verbo(V) :- verbo_simple(V, condicional).
verbo(V) :- verbo_simple(V, presente).
verbo(V) :- verbo_compuesto(V1, condicional), verbo_simple(V2, infinitivo), V = [V1, V2].
verbo(V) :- verbo_compuesto(V1, presente), verbo_simple(V2, infinitivo), V = [V1, V2].

verbo_simple(V, Conj) :- es_verbo_simp(Vp), raiz(Vp, Conj, R), declinacion(Vp, D), conjuga(Conj, D, T), atom_concat(R, T, V).
verbo_compuesto(V, Conj) :- es_verbo_comp(Vp), raiz(Vp, Conj, R), declinacion(Vp, D), conjuga(Conj, D, T), atom_concat(R, T, V).

es_verbo_simp(decir).
es_verbo_simp(listar).

es_verbo_comp(poder).
es_verbo_comp(querer).

raiz(decir, infinitivo, decir).
raiz(decir, condicional, di).
raiz(decir, presente, dic).

raiz(listar, infinitivo, listar).
raiz(listar, condicional, lista).
raiz(listar, presente, list).

raiz(poder, infinitivo, poder).
raiz(poder, condicional, pod).
raiz(poder, presente, pued).

raiz(querer, infinitivo, querer).
raiz(querer, condicional, quer).
raiz(querer, presente, quier).

declinacion(poder, er).
declinacion(querer, er).
declinacion(decir, ir).
declinacion(listar, ar).

conjuga(presente, ar, as).
conjuga(presente, er, es).
conjuga(presente, ir, es).
conjuga(condicional, _, rias).
conjuga(infinitivo, _, '').


cd(C, Objeto) :- determinante(G, N, Det), objeto(G, N, Objeto), C = [Det, Objeto].

determinante(masculino, plural, los).
determinante(femenino, plural, las).
objeto(masculino, plural, ingredientes).
objeto(femenino, plural, alergias).
