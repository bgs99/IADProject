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
    requestSupport (context, args) {
      axios('/missions/support/apply', {
        method: 'POST',
        params: args
      }).catch(error => {
        if (!context.getters.mock) {
          console.log(error);
        }
      })
    },
    requestSeen (context, id) {
      axios('/missions/support/send', {
        params: {
          id: id
        },
        method: 'POST'
      }).then(response => {
        let mr = context.state.requests.filter(q => q.id !== +id);
        let pr = {...context.state.requests.find(q => q.id === +id), seen: true};
        context.commit('setRequests', [...mr, pr]);
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
      context.commit('setTabs', [], {root: true});
      axios('/missions/support/process', {
        params: {
          page: page
        },
        method: 'GET'
      }).then(response => {
        context.commit('setRequests', response.data);
      }).catch(error => {
        if (!context.getters.mock) {
          console.log(error);
        } else {
          context.commit('setRequests', mockRequests);
        }
      })
    },
    loadRequestsByMission (context, mission) {
      context.commit('setTabs', [], {root: true});
      axios('/missions/support/process', {
        params: {
          mission: mission
        },
        method: 'GET'
      }).then(response => {
        context.commit('setRequests', response.data);
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
