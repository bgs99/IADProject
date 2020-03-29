import Vue from 'vue';
import Vuex from 'vuex';
import axios from 'axios';
import mockMap from './mock/mmap';
import mockMissions from './mock/mmissions'
import targets from './modules/targets';
import missions from './modules/missions';
import agents from './modules/agents';
import registry from './modules/registry';
import equipment from './modules/equipment'
import reports from './modules/reports'
import requests from './modules/requests'
import repair from './modules/repair'

import router from '../router'

Vue.use(Vuex);

export default new Vuex.Store({
  modules: {
    targets: targets,
    missions: missions,
    agents: agents,
    registry: registry,
    equipment: equipment,
    reports: reports,
    requests: requests,
    repair: repair
  },
  state: {
    failedLogin: false,
    mock: false,
    map: {
      id: -1
    },
    user: null,
    tabs: [],
    currentMission: null
  },
  mutations: {
    fail (state) {
      state.failedLogin = true;
    },
    setMapId (state, id) {
      state.map = {
        ...state.map,
        id: id
      };
    },
    setMap (state, map) {
      state.map = map;
    },
    setTabs (state, list) {
      state.tabs = list;
    },
    setMission (state, id) {
      state.currentMission = {
        id: id,
        ready: false
      };
    },
    selectWeapon (state, id) {
      state.currentMission = {
        ...state.currentMission,
        weapon: id
      };
      state.tabs = [];
    },
    selectTransport (state, id) {
      state.currentMission = {
        ...state.currentMission,
        transport: id
      };
      state.tabs = [];
    },
    selectCover (state, id) {
      state.currentMission = {
        ...state.currentMission,
        cover: id
      };
      state.tabs = [];
    },
    setReady (state) {
      state.currentMission = {
        ...state.currentMission,
        ready: true
      }
    },
    setUser (state, val) {
      state.user = val;
      state.failedLogin = false;
      if (val !== null) {
        state.currentMission = {
          id: val.currentMission
        };
      }
    },
    finishMission (state) {
      state.currentMission = null;
    },
    updateStatus (state, val) {
      const newMission = {
        ...state.missions.missions
          .find(q => q.id === state.currentMission.id),
        status: val
      }
      const newMissionList = [
          ...state.missions.missions.filter(q => q.id !== state.currentMission.id),
          newMission
        ]
      state.missions.missions = newMissionList;
      if (val === 'Выполнена') {
        state.currentMission = {
          id: null
        }
      }
    }
  },
  actions: {
    logout (context) {
      axios('/logout', {
        method: 'POST'
      }).then(a => {
        window.location.reload();
      }).catch(error => {
        if (!context.getters.mock) {
          console.log(error);
        } else {
          window.location.reload();
        }
      });
    },
    login (context, {login, password}) {
      axios('/login', {
        params: {
          login: login,
          password: password
        },
        headers: {
          'Accept': 'application/json'
        },
        method: 'POST'
      }).then(response => {
        if (response === undefined || response.data === null || response.data === undefined || response.data.id === undefined) {
          context.commit('fail');
        } else {
          context.commit('setUser', response.data);
        }
      }).catch(error => {
        if (!context.getters.mock) {
          console.log(error);
          context.commit('fail');
        } else {
          context.commit('setUser', {
            role: 'Отдел полевых операций', // 'Тайная полиция',
            id: 16,
            level: 5,
            deptId: 28,
            types: ['Выгулять собаку', 'Устранение утечки'],
            currentMission: null
          });
        }
      });
    },
    setStatus (context, val) {
      axios('/missions/update', {
        params: {
          id: context.state.currentMission.id,
          status: val
        },
        method: 'POST'
      }).then(response => {
        context.commit('updateStatus', val);
      }).catch(error => {
        if (!context.getters.mock) {
          console.log(error);
        } else {
          mockMissions.find(q => q.id === context.state.currentMission.id).status = val;
          if (val === 'Выполнена') {
            context.commit('finishMission');
          }
        }
      })
    },
    participate (context, id) {
      context.commit('setMission', +id);
    },
    startMission (context) {
      axios('/missions/start', {
        params: {
          id: context.state.currentMission.id
        },
        method: 'POST'
      }).then(response => {
        context.dispatch('loadMissionsById', context.state.currentMission.id);
      }).catch(error => {
        if (!context.getters.mock) {
          console.log(error);
        } else {
          mockMissions.find(q => q.id === context.state.currentMission.id).status = 'Выполняется';
        }
      })
    },
    missionReady (context) {
      axios('/missions/apply', {
        params: context.state.currentMission,
        method: 'POST'
      }).then(response => {
        context.commit('setReady');
        context.dispatch('loadMissionsById', context.state.currentMission.id);
      }).catch(error => {
        if (!context.getters.mock) {
          console.log(error);
        } else {
          context.commit('setReady');
        }
      })
    },
    changeMap (context, id) {
      if (context.getters.mapId === +id) {
        if (+id === -1) {
          router.replace('/map/0');
        }

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
    admin: state => state.user === null ? false : state.user.role === 'Тайная полиция',
    field: state => state.user === null ? false : state.user.role === 'Отдел полевых операций',
    map: state => state.map,
    mapId: state => state.map.id,
    mock: state => state.mock,
    missionId: state => state.currentMission !== null ? state.currentMission.id : null,
    fixer: state => state.user === null ? false : ['Оружейный склад', 'Ангары и гаражи'].includes(state.user.role),
    transportman: state => state.user === null ? false : 'Ангары и гаражи' === state.user.role
  }
});
