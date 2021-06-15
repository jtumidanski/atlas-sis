package rest

import (
	"atlas-sis/skill"
	"context"
	"github.com/gorilla/mux"
	"github.com/sirupsen/logrus"
	"net/http"
	"sync"
)

func CreateRestService(l *logrus.Logger, ctx context.Context, wg *sync.WaitGroup) {
	go NewServer(l, ctx, wg, ProduceRoutes())
}

func ProduceRoutes() func(l logrus.FieldLogger) http.Handler {
	return func(l logrus.FieldLogger) http.Handler {
		router := mux.NewRouter().PathPrefix("/ms/sis").Subrouter().StrictSlash(true)
		router.Use(CommonHeader)

		sr := router.PathPrefix("/skills").Subrouter()
		sr.HandleFunc("/{skillId}", skill.HandleGetSkillRequest(l)).Methods(http.MethodGet)
		return router
	}
}
