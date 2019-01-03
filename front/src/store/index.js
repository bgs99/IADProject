import Vue from 'vue'
import Vuex from 'vuex'
import axios from 'axios'
import mmap from './mock/mmap'
import mpeople from './mock/mpeople'

Vue.use(Vuex);

export default new Vuex.Store({
  state: {
    mock: true,
    map: {
      id: -1
    },
    people: []
  },
  mutations: {
    setMapId (state, id) {
      state.map = {...state.map, id: id};
    },
    setMap (state, map) {
      state.map = map;
    },
    setPeople (state, list) {
      state.people = list;
    }
  },
  actions: {
    changeMap (context, id) {
      if(context.state.map.id === +id)
        return;
      context.commit('setMapId', +id);
      context.dispatch('loadMap');
    },
    loadMap (context) {
      const id = +context.state.map.id;
      axios('/place', {
        params: {
          id: id
        },
        method: 'GET'
      }).then(response => {
        context.commit('setMap', response.data);
      }).catch(error => {
        if(!context.state.mock) console.log(error);
        else context.commit('setMap', mmap.find(q => q.id === id));
      })
    },
    loadPeopleByLocation (context) {
      axios('/place/locals', {
        params: {
          id: context.state.map.id
        },
        method: 'GET'
      }).then(response => {
        context.commit('setPeople', response.data);
      }).catch(error => {
        if(!context.state.mock) console.log(error);
        else context.commit('setPeople', mpeople);
      })
    }
  },
  getters: {
    map: state => state.map,
    people: state => state.people
  }
});
