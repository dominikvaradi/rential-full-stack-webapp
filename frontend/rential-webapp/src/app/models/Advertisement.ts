export class Advertisement {
    id: number;

    name: string;

    description: string;

    username: string;

    constructor(id: number, name: string, description: string, username: string) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.username = username;
    }
}