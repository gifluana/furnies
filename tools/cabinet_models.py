import os
import json

# Function to generate block models
def get_block_model(wood_type, shape):
    return {
        "parent": f"furnies:block/template/cabinet_{shape}",
        "textures": {
            "2": f"furnies:block/cabinet/{wood_type}"
        }
    }

# Function to generate item model
def get_item_model(wood_type):
    return {
        "parent": f"furnies:block/cabinet/{wood_type}_closed_left"
    }

# Function to generate blockstate JSON data
def get_blockstate(wood_type):
    return {
        "variants": {
            "facing=north,open=false,hinge=left": {
                "model": f"furnies:block/cabinet/{wood_type}_closed_left"
            },
            "facing=east,open=false,hinge=left": {
                "model": f"furnies:block/cabinet/{wood_type}_closed_left",
                "y": 90
            },
            "facing=south,open=false,hinge=left": {
                "model": f"furnies:block/cabinet/{wood_type}_closed_left",
                "y": 180
            },
            "facing=west,open=false,hinge=left": {
                "model": f"furnies:block/cabinet/{wood_type}_closed_left",
                "y": 270
            },

            "facing=north,open=false,hinge=right": {
                "model": f"furnies:block/cabinet/{wood_type}_closed_right"
            },
            "facing=east,open=false,hinge=right": {
                "model": f"furnies:block/cabinet/{wood_type}_closed_right",
                "y": 90
            },
            "facing=south,open=false,hinge=right": {
                "model": f"furnies:block/cabinet/{wood_type}_closed_right",
                "y": 180
            },
            "facing=west,open=false,hinge=right": {
                "model": f"furnies:block/cabinet/{wood_type}_closed_right",
                "y": 270
            },

            "facing=north,open=true,hinge=left": {
                "model": f"furnies:block/cabinet/{wood_type}_open_left"
            },
            "facing=east,open=true,hinge=left": {
                "model": f"furnies:block/cabinet/{wood_type}_open_left",
                "y": 90
            },
            "facing=south,open=true,hinge=left": {
                "model": f"furnies:block/cabinet/{wood_type}_open_left",
                "y": 180
            },
            "facing=west,open=true,hinge=left": {
                "model": f"furnies:block/cabinet/{wood_type}_open_left",
                "y": 270
            },

            "facing=north,open=true,hinge=right": {
                "model": f"furnies:block/cabinet/{wood_type}_open_right"
            },
            "facing=east,open=true,hinge=right": {
                "model": f"furnies:block/cabinet/{wood_type}_open_right",
                "y": 90
            },
            "facing=south,open=true,hinge=right": {
                "model": f"furnies:block/cabinet/{wood_type}_open_right",
                "y": 180
            },
            "facing=west,open=true,hinge=right": {
                "model": f"furnies:block/cabinet/{wood_type}_open_right",
                "y": 270
            }
        }
    }

# Function to save JSON file
def save_json_file(path, data):
    os.makedirs(os.path.dirname(path), exist_ok=True)
    with open(path, "w", encoding="utf-8") as f:
        json.dump(data, f, indent=4)

# Main function
def generate_files():
    # Read wood types from the JSON file in the same directory
    script_dir = os.path.dirname(os.path.abspath(__file__))
    wood_types_file = os.path.join(script_dir, "woods.json")

    with open(wood_types_file, "r", encoding="utf-8") as f:
        wood_types = json.load(f)

    # Base directory for furnies
    base_dir = os.path.join(script_dir, "furnies")
    shapes = ["closed_left", "closed_right", "open_left", "open_right"]

    # Generate files for each wood type
    for wood_type in wood_types:
        # Block models
        for shape in shapes:
            block_model_path = os.path.join(base_dir, f"models/block/cabinet/{wood_type}_{shape}.json")
            save_json_file(block_model_path, get_block_model(wood_type, shape))

        # Item model
        item_model_path = os.path.join(base_dir, f"models/item/{wood_type}_cabinet.json")
        save_json_file(item_model_path, get_item_model(wood_type))
        
        # Blockstate file
        blockstate_path = os.path.join(base_dir, f"blockstates/{wood_type}_cabinet.json")
        save_json_file(blockstate_path, get_blockstate(wood_type))

if __name__ == "__main__":
    generate_files()
