import axios from 'axios';
import mockMissions from '../mock/mmissions';
import mockTargets from '../mock/mtargets';
import router from '../../router'
export default {
  state: {
    missions: []
  },
  mutations: {
    setMissions (state, list) {
      state.missions = list;
    }
  },
  actions: {
    addMission (context, {type, level, desc}) {
      context.commit('setTabs', [], {root: true});
      const target = context.getters.targets[0];
      const id = target.id;
      axios('/missions/create', {
        params: {
          id: id,
          type: type,
          level: level,
          desc: desc
        },
        method: 'POST'
      }).then(response => {
        context.commit('setMission', response.data, {root: true});
        router.replace(`/missions/id/${context.getters.missionId}`);
      }).catch(error => {
        if (!context.getters.mock) {
          console.log(error);
        } else {
          mockMissions.unshift({
            id: -1,
            targetName: target.name,
            targetId: target.id,
            status: 'Создана',
            type: type,
            level: level,
            desc: desc,
            danger: target.danger,
            location: target.location,
            team: [],
            transport: [],
            minimalTeam: 0
          });
          context.commit('setMissions', mockMissions);
          mockTargets.find(q => q.id === id).active = false;
          context.commit('setMission', -1, {root: true});
        }
      });
    },
    loadMissionsByPage (context, page) {
      context.commit('setTabs', [], {root: true});
      axios('/missions', {
        params: {
          page: page
        },
        method: 'GET'
      }).then(response => {
        context.commit('setMissions', response.data);
      }).catch(error => {
        if (!context.getters.mock) {
          console.log(error);
        } else {
          context.commit('setMissions', mockMissions);
        }
      })
    },
    loadMissionsById (context, id) {
      context.commit('setTabs', [], {root: true});
      axios('/missions', {
        params: {
          id: id
        },
        method: 'GET'
      }).then(response => {
        context.commit('setMissions', response.data);
      }).catch(error => {
        if (!context.getters.mock) {
          console.log(error);
        } else {
          context.commit('setMissions', mockMissions.filter(q => q.id === +id));
        }
      })
    }
  },
  getters: {
    missions: state => state.missions
  }
}
