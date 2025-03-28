db = db.getSiblingDB("tasty-task"); // switch to the desired database

const fs = require('fs');
const path = require('path');

async function importData() {
    const seedDataDir = path.join(__dirname, 'seed-data');
    const files = fs.readdirSync(seedDataDir);
    files.forEach(file => {
        if (file.endsWith('.json')) {
            const collectionName = file.replace('.json', '');
            print(collectionName)
            const jsonData = JSON.parse(fs.readFileSync(`${seedDataDir}/${file}`, 'utf8'));
            db[collectionName].insertMany(jsonData);
        }
    });
}

importData();