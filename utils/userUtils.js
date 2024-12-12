const { nanoid } = require('nanoid');

exports.generateUserId = () => nanoid(10);
