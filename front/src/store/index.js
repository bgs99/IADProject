import Vue from 'vue';
import Vuex from 'vuex';
import axios from 'axios';
import mockMap from './mock/mmap';
import targets from './modules/targets';
import missions from './modules/missions';
import agents from './modules/agents';
import people from './modules/people';

Vue.use(Vuex);

export default new Vuex.Store({
  modules: {
    targets: targets,
    missions: missions,
    agents: agents,
    people: people
  },
  state: {
    mock: true,
    map: {
      id: -1
    },
    user: {
      role: 'admin',
      id: 16,
      level: 5,
      deptId: 28,
      currentMission: undefined
    }
  },
  mutations: {
    setMapId (state, id) {
      state.map = {
        ...state.map,
        id: id
      };
    },
    setMap (state, map) {
      state.map = map;
    }
  },
  actions: {
    changeMap (context, id) {
      if (context.getters.mapId === +id) {
        return;
      }
      context.commit('setMapId', +id);
      context.dispatch('loadMap');
    },
    loadMap (context) {
      const id = context.getters.mapId;
      axios('/place', {
        params: {
          id: id
        },
        method: 'GET'
      }).then(response => {
        context.commit('setMap', response.data);
      }).catch(error => {
        if (!context.getters.mock) {
          console.log(error);
        } else {
          const fmap = mockMap.find(q => q.id === id);
          if (fmap !== undefined) {
            context.commit('setMap', fmap);
          }
        }
      })
    }
  },
  getters: {
    admin: state => state.user.role === 'admin',
    map: state => state.map,
    mapId: state => state.map.id,
    mock: state => state.mock
  }
});
