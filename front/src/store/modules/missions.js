import axios from 'axios';
import mockMissions from '../mock/mmissions';
import mockTargets from '../mock/mtargets';

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
      const target = context.getters.targets[0];
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
        if (!context.getters.mock) {
          console.log(error);
        } else {
          mockMissions.unshift({
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
          context.commit('setMissions', mockMissions);
          mockTargets.find(q => q.id === id).active = false;
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
        if (!context.getters.mock) {
          console.log(error);
        } else {
          context.commit('setMissions', mockMissions);
        }
      })
    }
  },
  getters: {
    missions: state => state.missions
  }
}
