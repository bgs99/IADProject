import axios from 'axios';
import mockRequests from '../mock/mrequests';

export default {
  state: {
    requests: []
  },
  mutations: {
    setRequests (state, list) {
      state.requests = list;
    }
  },
  actions: {
    requestSeen (context, id) {
      axios('/missions/support/send', {
        params: {
          id: id
        },
        method: 'POST'
      }).then(response => {
        let mr = context.state.requests.filter(q => q.id !== +id);
        let pr = {...context.state.requests.find(q => q.id === +id), seen: true};
        mr.unshift(pr);
      }).catch(error => {
        if (!context.getters.mock) {
          console.log(error);
        } else {
          mockRequests.find(q => q.id === +id).seen = true;
          let mr = context.state.requests.filter(q => q.id !== +id);
          let pr = {...context.state.requests.find(q => q.id === +id), seen: true};
          mr.unshift(pr);
        }
      })
    },
    loadRequestsByPage (context, page) {
      axios('/missions/support/process', {
        params: {
          page: page
        },
        method: 'GET'
      }).then(response => {
        context.commit('setRequests', response.body);
      }).catch(error => {
        if (!context.getters.mock) {
          console.log(error);
        } else {
          context.commit('setRequests', mockRequests);
        }
      })
    },
    loadRequestsByMission (context, mission) {
      axios('/missions/support/process', {
        params: {
          mission: mission
        },
        method: 'GET'
      }).then(response => {
        context.commit('setRequests', response.body);
      }).catch(error => {
        if (!context.getters.mock) {
          console.log(error);
        } else {
          context.commit('setRequests', mockRequests.filter(q => q.mission.first === +mission));
        }
      })
    }
  },
  getters: {
    requests: state => state.requests
  }
}
