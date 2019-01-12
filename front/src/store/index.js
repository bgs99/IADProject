import Vue from 'vue'
import Vuex from 'vuex'
import axios from 'axios'
import mmap from './mock/mmap'
import mpeople from './mock/mpeople'
import magents from './mock/magents'

Vue.use(Vuex);

export default new Vuex.Store({
  state: {
    mock: false,
    map: {
      id: -1
    },
    people: [],
    agents: [],
    user: {
      role: 'admin',
      id: 16,
      level: 5,
      deptId: 28
    }
  },
  mutations: {
    setMapId (state, id) {
      state.map = {...state.map, id: id}
    },
    setMap (state, map) {
      state.map = map
    },
    setPeople (state, list) {
      state.people = list
    },
    setAgents (state, list) {
      state.agents = list
    },
    setSalary (state, {id, wage}) {
      state.agents.find(q => q.id === id).salary = wage
    },
    rank (state, {id, d}) {
      state.agents.find(q => q.id === id).level += d;
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
        if (!context.state.mock) {
          console.log(error);
        } else {
          const fmap = mmap.find(q => q.id === id);
          if (fmap !== undefined) {
            context.commit('setMap', fmap);
          }
        }
      })
    },
    loadPeopleByLocation (context) {
      const id = context.getters.mapId;
      axios('/place/locals', {
        params: {
          id: id
        },
        method: 'GET'
      }).then(response => {
        context.commit('setPeople', response.data);
      }).catch(error => {
        if (!context.state.mock) {
          console.log(error);
        } else {
          context.commit('setPeople', mpeople.filter(q => q.location.first === id));
        }
      })
    },
    loadAllPeople (context, page) {
      axios('/registry/people', {
        params: {
          page: page
        },
        method: 'GET'
      }).then(response => {
        context.commit('setPeople', response.data);
      }).catch(error => {
        if (!context.state.mock) {
          console.log(error);
        } else {
          context.commit('setPeople', mpeople);
        }
      })
    },
    loadAgentsByLocation (context) {
      const id = context.getters.mapId;
      axios('/place/locals/agents', {
        params: {
          id: id
        },
        method: 'GET'
      }).then(response => {
        context.commit('setAgents', response.data);
      }).catch(error => {
        if (!context.state.mock) {
          console.log(error);
        } else {
          context.commit('setAgents', magents);
        }
      })
    },
    loadAllAgents (context, page) {
      axios('/agents', {
        params: {
          page: page
        },
        method: 'GET'
      }).then(response => {
        context.commit('setAgents', response.data);
      }).catch(error => {
        if (!context.state.mock) {
          console.log(error);
        } else {
          context.commit('setAgents', magents);
        }
      })
    },
    updateSalary (context, {id, wage}) {
      const lid = +id;
      const oldWage = context.state.agents.find(q => q.id === lid).salary;
      if (oldWage === +wage) {
        return;
      }
      axios('/agents/wage/set', {
        params: {
          id: lid,
          wage: +wage
        },
        method: 'POST'
      }).catch(error => {
        if (!context.state.mock) {
          console.log(error);
        }
      });
      context.commit('setSalary', {id: lid, wage: +wage});
    },
    promote (context, id) {
      const lid = +id;
      axios('/agents/promote', {
        params: {
          id: lid
        },
        method: 'POST'
      }).catch(error => {
        if (!context.state.mock) {
          console.log(error);
        }
      });
      context.commit('rank', {id: lid, d: +1});
    },
    demote (context, id) {
      const lid = +id;
      axios('/agents/demote', {
        params: {
          id: lid
        },
        method: 'POST'
      }).catch(error => {
        if (!context.state.mock) {
          console.log(error);
        }
      });
      context.commit('rank', {id: lid, d: -1});
    }
  },
  getters: {
    map: state => state.map,
    mapId: state => state.map.id,
    people: state => state.people,
    agents: state => state.agents
  }
});
