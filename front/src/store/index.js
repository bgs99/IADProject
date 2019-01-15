import Vue from 'vue'
import Vuex from 'vuex'
import axios from 'axios'
import mmap from './mock/mmap'
import mpeople from './mock/mpeople'
import magents from './mock/magents'
import mmissions from './mock/mmissions'
import mtargets from './mock/mtargets'

Vue.use(Vuex);

export default new Vuex.Store({
  state: {
    mock: true,
    map: {
      id: -1
    },
    people: [],
    agents: [],
    user: {
      role: 'admin',
      id: 16,
      level: 5,
      deptId: 28,
      currentMission: undefined
    },
    missions: [],
    targets: []
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
    },
    setPeople (state, list) {
      state.people = list;
    },
    setAgents (state, list) {
      state.agents = list;
    },
    setMissions (state, list) {
      state.missions = list;
    },
    setTargets (state, list) {
      state.targets = list;
    },
    setSalary (state, {id, wage}) {
      state.agents.find(q => q.id === id).salary = wage;
    },
    rank (state, {id, d}) {
      state.agents.find(q => q.id === id).level += d;
    }
  },
  actions: {
    addMission (context, {type, level, desc}) {
      const target = context.state.targets[0];
      const id = target.id;
      axios('/missions/create', {
        params: {
          id: id,
          type: type,
          level: level,
          desc: desc
        },
        method: 'GET'
      }).catch(error => {
        if (!context.state.mock) {
          console.log(error);
        } else {
          mmissions.unshift({
            id: -1,
            targetName: target.name,
            target: target,
            status: 'Создана',
            type: type,
            level: level,
            desc: desc,
            danger: target.danger,
            location: target.location,
            team: [],
            transport: []
          });
          context.commit('setMissions', mmissions);
          mtargets.find(q => q.id === id).active = false;
        }
      })
    },
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
    loadPeopleByPage (context, page) {
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
    loadAgentsByPage (context, page) {
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
    loadAgentsById (context, id) {
      const lid = +id;
      axios('/agents', {
        params: {
          id: lid
        },
        method: 'GET'
      }).then(response => {
        context.commit('setAgents', response.data);
      }).catch(error => {
        if (!context.state.mock) {
          console.log(error);
        } else {
          context.commit('setAgents', magents.filter(q => +q.id === lid));
        }
      })
    },
    loadMissionsByPage (context, page) {
      axios('/missions', {
        params: {
          page: page
        },
        method: 'GET'
      }).then(response => {
        context.commit('setMissions', response.data);
      }).catch(error => {
        if (!context.state.mock) {
          console.log(error);
        } else {
          context.commit('setMissions', mmissions);
        }
      })
    },
    loadTargetsByLocation (context) {
      const id = context.getters.mapId;
      axios('/place/locals/targets', {
        params: {
          id: id
        },
        method: 'GET'
      }).then(response => {
        context.commit('setTargets', response.data);
      }).catch(error => {
        if (!context.state.mock) {
          console.log(error);
        } else {
          context.commit('setTargets', mtargets);
        }
      })
    },
    loadTargetsByPage (context, page) {
      axios('/targets', {
        params: {
          page: page
        },
        method: 'GET'
      }).then(response => {
        context.commit('setTargets', response.data);
      }).catch(error => {
        if (!context.state.mock) {
          console.log(error);
        } else {
          context.commit('setTargets', mtargets);
        }
      })
    },
    loadTargetsById (context, id) {
      const lid = +id;
      axios('/targets', {
        params: {
          id: lid
        },
        method: 'GET'
      }).then(response => {
        context.commit('setTargets', response.data);
      }).catch(error => {
        if (!context.state.mock) {
          console.log(error);
        } else {
          context.commit('setTargets', mtargets.filter(q => +q.id === lid));
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
    admin: state => state.user.role === 'admin',
    map: state => state.map,
    mapId: state => state.map.id,
    people: state => state.people,
    agents: state => state.agents,
    missions: state => state.missions
  }
});
