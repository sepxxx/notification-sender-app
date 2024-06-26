import axios from 'axios';

class RecipientsService {
    constructor(baseUrl, servicePrefix) {
        this.baseUrl = baseUrl;
        this.servicePrefix = servicePrefix;
    }

    getRecipientsLists() {
        return axios.get(`${this.baseUrl}/${this.servicePrefix}/lists`)
            .then(response => response.data)
            .catch(error => {
                console.log(error);
                return Promise.reject(error);
            });
    }

    saveRecipientList(formData) {
        return axios.post(
            `${this.baseUrl}/${this.servicePrefix}/lists/upload`,
            formData,
            {
                headers: {
                    'Content-Type': 'multipart/form-data'
                }
            })
            .then(response => response.data)
            .catch(error => {
                console.log(error);
                return Promise.reject(error);
            });
    }
    deleteRecipientList(listName) {
        // console.log("deleteRecipientList: ", listName);
        return axios.delete(
            `${this.baseUrl}/${this.servicePrefix}/lists`, {
                data: {
                    listName: listName
                }
            } )
            .then(response => response.data)
            .catch(error => {
                console.log(error);
                return Promise.reject(error);
            });
    }

    extendRecipientList(formData) {
        return axios.put(
            `${this.baseUrl}/${this.servicePrefix}/lists/upload`,
            formData,
    {
            headers: {
                'Content-Type': 'multipart/form-data'
            }
            })
            .then(response => response.data)
            .catch(error => {
                console.log(error);
                return Promise.reject(error);
            });
    }

    unionRecipientLists(data) {
        return axios.put(
            `${this.baseUrl}/${this.servicePrefix}/lists/union`,
            data)
            .then(response => response.data)
            .catch(error => {
                console.log(error);
                return Promise.reject(error);
            });
    }
}

export default new RecipientsService('http://localhost:8080', 'recipients-service');