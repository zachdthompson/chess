package dataaccess;

import dataaccess.dao.*;

public record DataAccess(MemoryUserDAO user, MemoryGameDAO game, MemoryAuthDAO auth) {}
