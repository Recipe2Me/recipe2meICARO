/* Objeto = gustos, alergias, nivel. Frase de información previa a la pregunta del formulario */
frase_previa_pregunta_formulario(F, Obj) :- sn(Sn, Suj), sv(Sv, Suj, Obj), append(Sn, Sv, F).

sn(Sn, Suj) :- comienzo(C), sujeto(Suj), Sn = [C, Suj].

comienzo(ahora).
comienzo([en, este, momento]).
comienzo([a, continuacion]).

sujeto(yo).
sujeto(tu).
sujeto('').

/* necesito saber tus gustos */
sv(Sv, yo, Obj) :- es_verbo_comp(noConj, Vp), conjuga(Vp, yo, V1), es_verbo_simp(Vp2), conjuga(Vp2, yo, V2), cd(C, Obj), Sv = [V1, V2, C].

/* tengo que saberme tus gustos */
sv(Sv, yo, Obj) :- es_verbo_comp(conj, Vp), conjuga(Vp, yo, V1), conjuncion(Conj), es_verbo_simp(Vp2), conjuga(Vp2, yo, V2), 
				pronombre_personal(P), cd(C, Obj), Sv = [V1, Conj, V2, P, C].
				
/* tienes que deicirme tus gustos */
sv(Sv, tu, Obj) :- es_verbo_comp(conj, Vp), conjuga(Vp, tu, V1), conjuncion(Conj), es_verbo_simp(Vp2), conjuga(Vp2, tu, V2), 
				pronombre_personal(P), cd(C, Obj), Sv = [V1, Conj, V2, P, C].
				
/* me tienes que deicir tus gustos */
sv(Sv, tu, Obj) :- es_verbo_comp(conj, Vp), conjuga(Vp, tu, V1), conjuncion(Conj), es_verbo_simp(Vp2), conjuga(Vp2, tu, V2), 
				pronombre_personal(P), cd(C, Obj), Sv = [P, V1, Conj, V2, C].

/* tienes que deicirme tus gustos */
sv(Sv, '', Obj) :- es_verbo_comp(conj, Vp), conjuga(Vp, tu, V1), conjuncion(Conj), es_verbo_simp(Vp2), conjuga(Vp2, tu, V2), 
				pronombre_personal(P), cd(C, Obj), Sv = [V1, Conj, V2, P, C].
				
/* me tienes que deicir tus gustos */
sv(Sv, '', Obj) :- es_verbo_comp(conj, Vp), conjuga(Vp, tu, V1), conjuncion(Conj), es_verbo_simp(Vp2), conjuga(Vp2, tu, V2), 
				pronombre_personal(P), cd(C, Obj), Sv = [P, V1, Conj, V2, C].

es_verbo_simp(saber).
es_verbo_simp(conocer).
es_verbo_simp(decir).

es_verbo_comp(noConj, necesitar).
es_verbo_comp(conj, tener).
es_verbo_comp(noConj, deber).

pronombre_personal(me).
pronombre_personal('').

conjuga(necesitar, yo, necesito).
conjuga(deber, yo, debo).
conjuga(deber, tu, debes).
conjuga(tener, tu, tienes).
conjuga(tener, yo, tengo).
conjuga(saber, yo, saber).
conjuga(conocer, yo, conocer).
conjuga(decir, tu, decir).

cd(C, Objeto) :- determinante(G, N, Det), objeto(G, N, Objeto), C = [Det, Objeto].

determinante(masculino, singular, tu).
determinante(femenino, singular, tu).
determinante(masculino, plural, tus).
determinante(femenino, plural, tus).
objeto(masculino, plural, gustos).
objeto(masculino, singular, nivel).
objeto(femenino, plural, alergias).

conjuncion(que).
