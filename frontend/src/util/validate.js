const validateMethods = {
  nameValidate: name => {
    const invalidNamePattern = /[-]/;
    if (invalidNamePattern.test(name)) {
      return false;
    }
    return true;
  },
};

export { validateMethods };
