package dataaccess;

import dataaccess.dao.*;

public record DataAccess(MemoryUserDAO UserDAO, MemoryGameDAO game, MemoryAuthDAO auth) {}
