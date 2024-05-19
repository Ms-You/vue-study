import { createStore } from 'vuex'

export default createStore({
  state: {
    isAuthenticated: false,
    searchText: "",
    searchResults: [],
  },
  mutations: {
    setAuthentication(state, status) {
      state.isAuthenticated = status;
    },
    logout(state) {
      state.isAuthenticated = false;
    },
    setSearchText(state, text) {
      state.searchText = text;
    },
    setSearchResults(state, items) {
      state.searchResults = items;
    }
  },
  actions: {
    initializeAuthentication({ commit }) {
      const accessToken = localStorage.getItem('accessToken');

      if(accessToken) {
        commit('setAuthentication', true);
      } else {
        commit('setAuthentication', false);
      }
    },
    logout({ commit }) {
      localStorage.removeItem('accessToken');
      commit('setAuthentication', false);
    },
    setSearchText({ commit }, text) {
      commit('setSearchText', text);
    },
    setSearchResults({ commit }, items) {
      commit('setSearchResults', items);
    }
  }
})
